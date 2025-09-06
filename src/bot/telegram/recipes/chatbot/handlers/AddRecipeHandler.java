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

import java.util.List;
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

            case ADD_REWIEW -> {
                if (callbackQuery.getData().startsWith("ADD:RECIPE:REWIEW")) {
                    handleStartEditRecipe(callbackQuery);
                } else if (callbackQuery.getData().startsWith("ADD:RECIPE:SAVE")) {
                    //TODO доделать реализацию
                } else {
                    //TODO Переделать обработчк для нужного экрана
                }
            }
            case EDIT_FIELD_SELECT -> {
                if (callbackQuery.getData().startsWith("EDIT:FIELD:SELECT:TITLE")) {
                    handleEditTitleRecipe(callbackQuery);
                } else if (callbackQuery.getData().startsWith("EDIT:FIELD:SELECT:STEPS")) {
                    handleEditStepsRecipe(callbackQuery);
                } else if (callbackQuery.getData().startsWith("EDIT:DONE")) {
                    handleEditDone(callbackQuery);
                } else if (callbackQuery.getData().startsWith("EDIT:FIELD:SELECT:INGREDIENTS")) {
                    handleEditIngredientsRecipe(callbackQuery);
                } else if (callbackQuery.getData().startsWith("EDIT:FIELD:SELECT:TYPE")) {
                    handleEditTypeRecipe(callbackQuery);
                } else {
                    //TODO Переделать обработчк для нужного экрана
                }
            }
            case EDIT_TYPE -> {
                if (!callbackQuery.getData().startsWith("ADD:TYPE:")) {
                    //TODO Переделать обработчк для нужного экрана
                } else {
                    handleAddNewTypeOfDish(callbackQuery);
                }
            }
            case EDIT_ING_LIST -> {
                if (callbackQuery.getData().startsWith("EDIT:ING:LIST:CHANGE")) {
                    handleStartChangeIng(callbackQuery);
                } else if (callbackQuery.getData().startsWith("EDIT:ING:LIST:REMOVE")) {
                    handleStartRemoveIng(callbackQuery);
                } else if (callbackQuery.getData().startsWith("EDIT:ING:LIST:ADD")) {
                    handleEditIngAddName(callbackQuery);
                } else if (callbackQuery.getData().startsWith("EDIT:ING:LIST:READY")) {
                    handleEditListIngDone(callbackQuery);
                }
            }

            case EDIT_ING_FIELD_SELECT -> {
                if (callbackQuery.getData().startsWith("EDIT:ING:TITLE")) {
                    handleEditTitleIng(callbackQuery);
                } else if (callbackQuery.getData().startsWith("EDIT:ING:UNIT")) {
                    handleEditUnitIng(callbackQuery);
                } else if (callbackQuery.getData().startsWith("EDIT:ING:COUNT")) {
                    handleEditCountIng(callbackQuery);
                } else if (callbackQuery.getData().startsWith("EDIT:ING:READY")) {
                    handleEditIngredientsRecipe(callbackQuery);
                }
            }

            case EDIT_ING_UNIT -> {
                if (!callbackQuery.getData().startsWith("ADD:UNIT:")) {
                    //TODO Переделать обработчк для нужного экрана
                } else {
                    handleAddNewUnitIng(callbackQuery);
                }
            }

            case EDIT_ING_ADD_UNIT -> {
                if (!callbackQuery.getData().startsWith("ADD:UNIT:")) {
                    telegramBotSender.sendMessage(chatId,
                            "Извините, но сейчас вам нужно выбрать единицу измерения для ингридиента, который хотите добавить :)");
                    //TODO Переделать обработчк для нужного экрана
                } else {
                    handleEditIngSelectedUnit(callbackQuery);
                }
            }

            case EDIT_ING_ADD_NAME -> {
                if (!callbackQuery.getData().startsWith("EDIT:ING:ADD:DONE")) {
                    telegramBotSender.sendMessage(chatId,
                            "Извините, но сейчас вам нужно ввести название нового ингредента или завершить добавление ингредиентов :)");
                    //TODO Переделать обработчк для нужного экрана
                } else {
                    handleEditIngAddNewDone(callbackQuery);
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
            case ADD_STEPS -> handleAddStepsForRecipe(message);
            case EDIT_TITLE -> handleAddNewTitleForRecipe(message);
            case EDIT_STEPS -> handleAddNewStepsForRecipe(message);
            case EDIT_ING -> handleChangeIng(message);
            case EDIT_ING_TITLE -> handleAddNewTitleForIng(message);
            case EDIT_ING_COUNT -> handleAddNewCountForIng(message);
            case REMOVE_ING -> handleRemoveIng(message);
            case EDIT_ING_ADD_NAME -> handleEditIngAddNameForIngredient(message);
            case EDIT_ING_ADD_COUNT -> handleEditIngAddCountForIngredient(message);
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
                MessageFormatter.UnitToString(unit) + ")",
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
                            MessageFormatter.UnitToString(userSession.getTempIngredient().getUnit()) + ")",
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

    public void handleAddStepsForRecipe(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);

        String steps = message.getText() == null ? "" : message.getText().trim();

        if (steps.isEmpty()) {
            telegramBotSender.sendMessageWithParseMode(chatId,
                    "Пошаговый рецепт не может быть пустым не может быть пустым, введите название ингридиента",
                    "HTML");
            return;
        }

        Recipe draftRecipe = userSession.getDarftRecipe();
        draftRecipe.setSteps(steps);

        userSession.setStage(Stage.ADD_REWIEW);

        telegramBotSender.sendMessage(chatId,
                "Формирование рецепта завершено!");

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.RecipeToString(draftRecipe),
                KeyboardFactory.doneOrRewiewRecipe(),
                "HTML");

    }

    public void handleStartEditRecipe(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);
        Recipe draftRecipe = userSession.getDarftRecipe();

        userSession.setStage(Stage.EDIT_FIELD_SELECT);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.RecipeToString(draftRecipe) + MessageFormatter.startEditRecipe(),
                KeyboardFactory.selectFieldForEditRecipe(),
                "HTML");

    }

    public void handleEditTitleRecipe(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        String title = draftRecipe.getTitle();

        userSession.setStage(Stage.EDIT_TITLE);

        telegramBotSender.sendMessage(chatId,
                MessageFormatter.editTitleRecipe(title));
    }

    public void handleEditStepsRecipe(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        String steps = draftRecipe.getSteps();

        userSession.setStage(Stage.EDIT_STEPS);

        telegramBotSender.sendMessage(chatId,
                MessageFormatter.editStepsRecipe(steps));
    }

    public void handleAddNewTitleForRecipe(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);

        String newTitle = message.getText() == null ? "" : message.getText().trim();

        if (newTitle.isEmpty()) {
            telegramBotSender.sendMessageWithParseMode(chatId,
                    "Название не может быть пустым, введите название блюда", "HTML");
            return;
        }

        Recipe draftRecipe = userSession.getDarftRecipe();
        draftRecipe.setTitle(newTitle);

        userSession.setStage(Stage.EDIT_FIELD_SELECT);

        telegramBotSender.sendMessage(chatId,
                MessageFormatter.editDone());

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.RecipeToString(draftRecipe) + MessageFormatter.startEditRecipe(),
                KeyboardFactory.selectFieldForEditRecipe(),
                "HTML");
    }

    public void handleAddNewStepsForRecipe(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);

        String newSteps = message.getText() == null ? "" : message.getText().trim();

        if (newSteps.isEmpty()) {
            telegramBotSender.sendMessageWithParseMode(chatId,
                    "Пошаговый рецепт не может быть пустым, введите пошаговый рецепт блюда", "HTML");
            return;
        }

        Recipe draftRecipe = userSession.getDarftRecipe();
        draftRecipe.setSteps(newSteps);

        userSession.setStage(Stage.EDIT_FIELD_SELECT);

        telegramBotSender.sendMessage(chatId,
                MessageFormatter.editDone());

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.RecipeToString(draftRecipe) + MessageFormatter.startEditRecipe(),
                KeyboardFactory.selectFieldForEditRecipe(),
                "HTML");
    }

    public void handleEditDone(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();

        userSession.setStage(Stage.ADD_REWIEW);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.RecipeToString(draftRecipe),
                KeyboardFactory.doneOrRewiewRecipe(),
                "HTML");
    }

    public void handleEditTypeRecipe(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        String typeOfDish = MessageFormatter.typeOfDishToString(draftRecipe.getType());

        userSession.setStage(Stage.EDIT_TYPE);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.editTypeOfDish(typeOfDish),
                KeyboardFactory.chooseTypeOfDish(),
                "HTML");
    }

    public void handleAddNewTypeOfDish(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();

        TypeOfDish newType = getTypeOfDish(callbackQuery);

        draftRecipe.setType(newType);

        userSession.setStage(Stage.EDIT_FIELD_SELECT);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.RecipeToString(draftRecipe) + MessageFormatter.startEditRecipe(),
                KeyboardFactory.selectFieldForEditRecipe(),
                "HTML");
    }

    public void handleEditIngredientsRecipe(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        List<Ingredient> ingredients = draftRecipe.getIngredients();

        userSession.setStage(Stage.EDIT_ING_LIST);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.editIngList(ingredients),
                KeyboardFactory.selectFieldForEditIngredients(),
                "HTML");
    }

    public void handleStartChangeIng(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        List<Ingredient> ingredients = draftRecipe.getIngredients();

        userSession.setStage(Stage.EDIT_ING);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.changeIng(ingredients),
                null,
                "HTML");
    }

    public void handleChangeIng(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        List<Ingredient> ingredients = draftRecipe.getIngredients();

        String text = message.getText() == null ? "" : message.getText().trim();

        Integer numberOfIng = null;

        try {
            numberOfIng = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            telegramBotSender.sendMessage(chatId,
                    "Введен неправильный формат числа (Пример: 1, 10, 110)");

            telegramBotSender.sendMessageWithParseMode(chatId,
                    MessageFormatter.changeIng(ingredients),
                    "HTML");
            return;
        }

        if (ingredients.size() < numberOfIng || numberOfIng <= 0) {
            telegramBotSender.sendMessage(chatId,
                    "Ингредиента с таким номером нет, повторите ввод");

            telegramBotSender.sendMessageWithParseMode(chatId,
                    MessageFormatter.changeIng(ingredients),
                    "HTML");
            return;
        }

        userSession.setLastIndex(numberOfIng);
        userSession.setStage(Stage.EDIT_ING_FIELD_SELECT);

        telegramBotSender.sendMessage(chatId,
                MessageFormatter.selectFieldForEditIng(ingredients.get(numberOfIng - 1), numberOfIng),
                KeyboardFactory.selectFieldForEditIng());
    }

    public void handleEditTitleIng(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Integer indexIng = userSession.getLastIndex();

        Recipe draftRecipe = userSession.getDarftRecipe();

        userSession.setStage(Stage.EDIT_ING_TITLE);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.editIngTitle(draftRecipe.getIngredients().get(indexIng - 1), indexIng),
                null,
                "HTML");
    }

    public void handleAddNewTitleForIng(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        Integer indexIng = userSession.getLastIndex();

        String newTitle = message.getText() == null ? "" : message.getText().trim();

        if (newTitle.isEmpty()) {
            telegramBotSender.sendMessage(chatId,
                    "Название ингредиента не может быть пустым, повторите ввод");
            return;
        }

        Ingredient ingredient = draftRecipe.getIngredients().get(indexIng - 1);
        ingredient.setTitle(newTitle);

        userSession.setStage(Stage.EDIT_ING_FIELD_SELECT);

        telegramBotSender.sendMessage(chatId,
                MessageFormatter.selectFieldForEditIng(draftRecipe.getIngredients().get(indexIng - 1), indexIng),
                KeyboardFactory.selectFieldForEditIng());
    }

    public void handleEditUnitIng(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Integer indexIng = userSession.getLastIndex();

        Recipe draftRecipe = userSession.getDarftRecipe();

        userSession.setStage(Stage.EDIT_ING_UNIT);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.editIngUnit(draftRecipe.getIngredients().get(indexIng - 1), indexIng),
                KeyboardFactory.chooseUnitOfIngredient(),
                "HTML");
    }

    public void handleAddNewUnitIng(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Unit newUnit = getUnitOfIngredient(callbackQuery);
        Recipe draftRecipe = userSession.getDarftRecipe();
        Integer indexIng = userSession.getLastIndex();

        Ingredient ingredient = draftRecipe.getIngredients().get(indexIng - 1);
        ingredient.setUnit(newUnit);

        userSession.setStage(Stage.EDIT_ING_FIELD_SELECT);

        telegramBotSender.sendMessage(chatId,
                MessageFormatter.selectFieldForEditIng(draftRecipe.getIngredients().get(indexIng - 1), indexIng),
                KeyboardFactory.selectFieldForEditIng());
    }

    public void handleEditCountIng(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Integer indexIng = userSession.getLastIndex();

        Recipe draftRecipe = userSession.getDarftRecipe();

        userSession.setStage(Stage.EDIT_ING_COUNT);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.editIngCount(draftRecipe.getIngredients().get(indexIng - 1), indexIng),
                null,
                "HTML");
    }

    public void handleAddNewCountForIng(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        Integer indexIng = userSession.getLastIndex();

        String newCount = (message.getText() == null ? "" : message.getText().trim());

        if (newCount.isEmpty()) {
            telegramBotSender.sendMessageWithParseMode(chatId,
                    "Количество не может быть пустым, введите количество ингридиента",
                    "HTML");
            return;
        }

        if ((!stringIsDouble(newCount)) || newCount.equals("0")) {
            telegramBotSender.sendMessage(chatId,
                    "Введен неправильный формат числа (Пример верного формата: 1.5 / 1,5 / 123)");

            telegramBotSender.sendMessageWithParseMode(chatId,
                    MessageFormatter.addCountForIngredient() + " (" +
                            MessageFormatter.UnitToString(userSession.getTempIngredient().getUnit()) + ")",
                    "HTML");

            return;
        }

        Ingredient ingredient = draftRecipe.getIngredients().get(indexIng - 1);
        ingredient.setCount(Double.parseDouble(newCount.replace(",", ".")));

        userSession.setStage(Stage.EDIT_ING_FIELD_SELECT);

        telegramBotSender.sendMessage(chatId,
                MessageFormatter.selectFieldForEditIng(draftRecipe.getIngredients().get(indexIng - 1), indexIng),
                KeyboardFactory.selectFieldForEditIng());
    }

    public void handleStartRemoveIng(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        List<Ingredient> ingredients = draftRecipe.getIngredients();

        userSession.setStage(Stage.REMOVE_ING);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.removeIng(ingredients),
                null,
                "HTML");
    }

    public void handleRemoveIng(Message message) {
        Long chatId = message.getChatId();
        Integer messageId = message.getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        List<Ingredient> ingredients = draftRecipe.getIngredients();

        String text = message.getText() == null ? "" : message.getText().trim();

        Integer numberOfIng = null;

        try {
            numberOfIng = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            telegramBotSender.sendMessage(chatId,
                    "Введен неправильный формат числа (Пример: 1, 10, 110)");

            telegramBotSender.sendMessageWithParseMode(chatId,
                    MessageFormatter.changeIng(ingredients),
                    "HTML");
            return;
        }

        if (ingredients.size() < numberOfIng || numberOfIng <= 0) {
            telegramBotSender.sendMessage(chatId,
                    "Ингредиента с таким номером нет, повторите ввод");

            telegramBotSender.sendMessageWithParseMode(chatId,
                    MessageFormatter.changeIng(ingredients),
                    "HTML");
            return;
        }

        ingredients.remove(numberOfIng - 1);

        userSession.setStage(Stage.EDIT_ING_LIST);

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.editIngList(ingredients),
                KeyboardFactory.selectFieldForEditIngredients(),
                "HTML");
    }

    public void handleEditIngAddName(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        userSession.setStage(Stage.EDIT_ING_ADD_NAME);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.addNameForIngredient(),
                KeyboardFactory.editIngEndForAdd(),
                "HTML");
    }

    public void handleEditIngAddNameForIngredient(Message message) {
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

        userSession.setStage(Stage.EDIT_ING_ADD_UNIT);

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.addUnitForIngredient(),
                KeyboardFactory.chooseUnitOfIngredient(),
                "HTML");
    }

    public void handleEditIngSelectedUnit(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        UserSession userSession = stateStore.get(chatId);

        Ingredient tempIngredient = userSession.getTempIngredient();
        Unit unit = getUnitOfIngredient(callbackQuery);
        tempIngredient.setUnit(unit);

        userSession.setStage(Stage.EDIT_ING_ADD_COUNT);

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.addCountForIngredient() + " (" +
                        MessageFormatter.UnitToString(unit) + ")",
                "HTML");
    }

    public void handleEditIngAddCountForIngredient(Message message) {
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
                            MessageFormatter.UnitToString(userSession.getTempIngredient().getUnit()) + ")",
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

        userSession.setStage(Stage.EDIT_ING_ADD_NAME);

        telegramBotSender.sendMessageWithParseMode(chatId,
                MessageFormatter.addNameForIngredient(),
                KeyboardFactory.editIngEndForAdd(),
                "HTML");
    }

    public void handleEditIngAddNewDone(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();
        List<Ingredient> ingredients = draftRecipe.getIngredients();

        userSession.setStage(Stage.EDIT_ING_LIST);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.editIngList(ingredients),
                KeyboardFactory.selectFieldForEditIngredients(),
                "HTML");
    }

    public void handleEditListIngDone(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        UserSession userSession = stateStore.get(chatId);

        Recipe draftRecipe = userSession.getDarftRecipe();

        userSession.setStage(Stage.EDIT_FIELD_SELECT);

        telegramBotSender.editMessageTextWithKeybord(chatId,
                messageId,
                MessageFormatter.RecipeToString(draftRecipe) + MessageFormatter.startEditRecipe(),
                KeyboardFactory.selectFieldForEditRecipe(),
                "HTML");
    }
}
