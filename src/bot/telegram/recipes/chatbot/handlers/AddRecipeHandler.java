package bot.telegram.recipes.chatbot.handlers;

import bot.telegram.recipes.chatbot.formatting.MessageFormatter;
import bot.telegram.recipes.chatbot.keyboards.KeyboardFactory;
import bot.telegram.recipes.chatbot.presentation.TelegramBotSender;
import bot.telegram.recipes.chatbot.state.Stage;
import bot.telegram.recipes.chatbot.state.StateStore;
import bot.telegram.recipes.chatbot.state.UserSession;
import bot.telegram.recipes.entities.Ingredient;
import bot.telegram.recipes.entities.Recipe;
import bot.telegram.recipes.entities.TypeOfDish;
import bot.telegram.recipes.entities.Unit;
import bot.telegram.recipes.service.RecipeService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Pattern;


public class AddRecipeHandler {
    private final StateStore stateStore;
    private final TelegramBotSender telegramBotSender;
    private final RecipeService recipeService;

    public AddRecipeHandler(StateStore stateStore, TelegramBotSender telegramBotSender, RecipeService recipeService) {
        this.stateStore = stateStore;
        this.telegramBotSender = telegramBotSender;
        this.recipeService = recipeService;
    }

    public void addCallback(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        UserSession userSession = stateStore.get(chatId);
        Stage currentStage = userSession.getStage();

        switch (currentStage) {
            case IDLE -> handleStartAddRecipe(callbackQuery);
            case ADD_TYPE -> {
                if (!callbackQuery.getData().startsWith("ADD:TYPE:")) {
                    telegramBotSender.sendMessage(chatId,
                            "Извините, но сейчас вам нужно выбрать тип блюда для рецепта, который хотите добавить :)");
                    //TODO Переделать обработчк для нужного экрана
                    handleStartAddRecipe(callbackQuery);
                } else {
                    handleSelectedType(callbackQuery);
                }
            }
            case ADD_ING_UNIT -> {
                if (!callbackQuery.getData().startsWith("ADD:UNIT:")) {
                    telegramBotSender.sendMessage(chatId,
                            "Извините, но сейчас вам нужно выбрать единицу измерения для ингридиента, который хотите добавить :)");
                    //TODO Переделать обработчк для нужного экрана
                    handleStartAddRecipe(callbackQuery);
                } else {
                    handleSelectedUnit(callbackQuery);
                }
            }
            case ADD_ING_NAME -> {
                if (!callbackQuery.getData().startsWith("ADD:ING:DONE")) {
                    telegramBotSender.sendMessage(chatId,
                            "Извините, но сейчас вам нужно ввести название нового ингредента или завершить добавление ингредиентов :)");
                    //TODO Переделать обработчк для нужного экрана
                    handleStartAddRecipe(callbackQuery);
                } else {
                    handleDoneIng(callbackQuery);
                }
            }
        }
    }

    public void addText(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);
        Stage stage = userSession.getStage();

        switch (stage) {
            case ADD_NAME -> handleAddName(message);
            case ADD_ING_NAME ->  handleAddNameForIngredient(message);
            case ADD_ING_COUNT -> handleAddCountForIngredient(message);
        }
    }

    public void handleStartAddRecipe(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        UserSession userSession = stateStore.get(chatId);
        userSession.setStage(Stage.ADD_TYPE);

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.startAddRecipe(),
                "HTML");

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.selectTypeOfDish(),
                KeyboardFactory.chooseTypeOfDish(),
                "HTML");

    }

    public void handleSelectedType(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        draftRecipe.setType(getTypeOfDish(callbackQuery));
        userSession.setStage(Stage.ADD_NAME);

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.addNameForDish(),
                "HTML");
    }

    public TypeOfDish getTypeOfDish(CallbackQuery callbackQuery) {
        String typeOfDish = callbackQuery.getData();

        switch (typeOfDish) {
            case "ADD:TYPE:MAIN" : return TypeOfDish.MAIN;
            case "ADD:TYPE:MEAT" : return TypeOfDish.MEAT;
            case "ADD:TYPE:SIDE" : return TypeOfDish.SIDE;
            case "ADD:TYPE:SALAD" : return TypeOfDish.SALAD;
            case "ADD:TYPE:SNACK" : return TypeOfDish.SNACK;
            default: return null;
        }
    }

    public void handleAddName(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);
        String title = message.getText() == null ? "" : message.getText().trim();

        if (title.isEmpty()) {
            telegramBotSender.sendMessageWithParseMode(chatId,
                    "Название не может быть пустым, введите название блюда", "HTML");
            return;
        }

        Recipe draftRecipe = userSession.getDarftRecipe();
        draftRecipe.setTitle(title);

        userSession.setStage(Stage.ADD_ING_NAME);

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.addNameForIngredient(),
                KeyboardFactory.endForAddIngredient(),
                "HTML");

    }

    public void handleAddNameForIngredient(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);
        String title = message.getText() == null ? "" : message.getText().trim();

        if (title.isEmpty()) {
            telegramBotSender.sendMessageWithParseMode(chatId,
                    "Название не может быть пустым, введите название ингридиента",
                    "HTML");
            return;
        }

        Ingredient tempIngredient = userSession.getTempIngredient();
        tempIngredient.setTitle(title);

        userSession.setStage(Stage.ADD_ING_UNIT);

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.addUnitForIngredient(),
                KeyboardFactory.chooseUnitOfIngredient(),
                "HTML");
    }

    public void handleSelectedUnit(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        UserSession userSession = stateStore.get(chatId);

        Ingredient tempIngredient = userSession.getTempIngredient();
        Unit unit = getUnitOfIngredient(callbackQuery);
        tempIngredient.setUnit(unit);

        userSession.setStage(Stage.ADD_ING_COUNT);

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.addCountForIngredient() + " (" +
                MessageFormatter.getUnitFromEnum(unit) + ")",
                "HTML");
    }

    public Unit getUnitOfIngredient(CallbackQuery callbackQuery) {
        String str = callbackQuery.getData();
        String[] split = str.split(":");
        String unit = split[2];

        switch (unit) {
            case "GRAM": return Unit.GRAM;
            case "MILLILITER": return Unit.MILLILITER;
            case "CUP": return Unit.CUP;
            case "TABLESPOON": return Unit.TABLESPOON;
            case "TEASPOON": return Unit.TEASPOON;
            default: return null;
        }
    }


    public void handleAddCountForIngredient(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);

        String count = (message.getText() == null ? "" : message.getText().trim());

        if (count.isEmpty()) {
            telegramBotSender.sendMessageWithParseMode(chatId,
                    "Количество не может быть пустым, введите количество ингридиента",
                    "HTML");
            return;
        }

        if ((!stringIsDouble(count)) || count.equals("0")) {
            telegramBotSender.sendMessage(chatId,
                    "Введен неправильный формат числа (Пример верного формата: 1.5 / 1,5 / 123)");

            telegramBotSender.sendMessageWithParseMode(chatId,
                    MessageFormatter.addCountForIngredient() + " (" +
                            MessageFormatter.getUnitFromEnum(userSession.getTempIngredient().getUnit()) + ")",
                    "HTML");

            return;
        }

        Ingredient tempIngredient = userSession.getTempIngredient();
        tempIngredient.setCount(Double.parseDouble(count.replace(",", ".")));

        if (!checkFullFieldIndredient(tempIngredient, message)) {
            return;
        }

        Recipe draftRecipe = userSession.getDarftRecipe();
        draftRecipe.getIngredients().add(tempIngredient);
        userSession.setTempIngredient(new Ingredient());

        userSession.setStage(Stage.ADD_ING_NAME);

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.addNameForIngredient(),
                KeyboardFactory.endForAddIngredient(),
                "HTML");
    }

    public boolean stringIsDouble(String str) {
        str = str.replaceAll(",", ".");
        return Pattern.matches("^\\d+(\\.\\d+)?$", str);
    }

    public boolean checkFullFieldIndredient(Ingredient ingredient, Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);

        if (ingredient.getTitle() == null || ingredient.getTitle().isEmpty()) {

            telegramBotSender.sendMessage(chatId,
                    "У ингридиента нет названия");

            userSession.setStage(Stage.ADD_ING_NAME);

            telegramBotSender.sendMessageWithParseMode(chatId,
                    MessageFormatter.addNameForDish(),
                    "HTML");

            return false;

        } else if (ingredient.getUnit() == null) {

            telegramBotSender.sendMessage(chatId,
                    "У ингридиента нет единицы измерения количества");

            userSession.setStage(Stage.ADD_ING_UNIT);

            telegramBotSender.sendMessageWithParseMode(chatId,
                    MessageFormatter.addUnitForIngredient(),
                    KeyboardFactory.chooseUnitOfIngredient(),
                    "HTML");

            return false;

        } else if (ingredient.getCount() == null) {

            telegramBotSender.sendMessage(chatId,
                    "У ингридиента нет количества");

            userSession.setStage(Stage.ADD_ING_COUNT);

            telegramBotSender.sendMessageWithParseMode(chatId,
                    MessageFormatter.addCountForIngredient(),
                    "HTML");

            return false;
        }

        return true;
    }

    public void handleDoneIng(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        UserSession userSession = stateStore.get(chatId);
        userSession.setStage(Stage.ADD_STEPS);

        telegramBotSender.sendMessage(chatId,
                "Вы завершили добавление ингредиентов, необходимых для блюда");

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.addStepsForRecipe(),
                "HTML");
    }
}
