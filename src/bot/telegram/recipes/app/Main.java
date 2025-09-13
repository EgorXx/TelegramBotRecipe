package bot.telegram.recipes.app;

import bot.telegram.recipes.cache.LookupDataCache;
import bot.telegram.recipes.chatbot.config.BotProperties;
import bot.telegram.recipes.chatbot.handlers.*;
import bot.telegram.recipes.chatbot.presentation.Bot;
import bot.telegram.recipes.chatbot.presentation.TelegramBotSender;
import bot.telegram.recipes.chatbot.state.InMemoryStateStore;
import bot.telegram.recipes.chatbot.state.StateStore;
import bot.telegram.recipes.repository.IngredientRepositoryJdbc;
import bot.telegram.recipes.repository.RecipeRepositoryJdbc;
import bot.telegram.recipes.repository.TypeOfDishRepositoryJdbc;
import bot.telegram.recipes.repository.UnitRepositoryJdbc;
import bot.telegram.recipes.repository.impl.IngredientRepositoryJdbcImpl;
import bot.telegram.recipes.repository.impl.RecipeRepositoryJdbcImpl;
import bot.telegram.recipes.repository.impl.TypeOfDishRepositoryJdbcImpl;
import bot.telegram.recipes.repository.impl.UnitRepositoryJdbcImpl;
import bot.telegram.recipes.service.RecipeService;
import bot.telegram.recipes.util.jdbc.JdbcConfig;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        BotProperties prop = BotProperties.load();
        Bot bot  = new Bot(prop.getUsername(), prop.getToken());
        TelegramBotSender telegramBotSender = new TelegramBotSender(bot);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        botsApi.registerBot(bot);

        //TODO репозитории сервисы (сервисы принимают репозитории)
        DataSource dataSource = JdbcConfig.getDataSource();

        RecipeRepositoryJdbc recipeRepositoryJdbc = new RecipeRepositoryJdbcImpl(dataSource);
        IngredientRepositoryJdbc ingredientRepositoryJdbc = new IngredientRepositoryJdbcImpl(dataSource);
        TypeOfDishRepositoryJdbc typeOfDishRepositoryJdbc = new TypeOfDishRepositoryJdbcImpl(dataSource);
        UnitRepositoryJdbc unitRepositoryJdbc = new UnitRepositoryJdbcImpl(dataSource);

        LookupDataCache.initialize(typeOfDishRepositoryJdbc, unitRepositoryJdbc);

        RecipeService recipeService = new RecipeService(recipeRepositoryJdbc, ingredientRepositoryJdbc);

        StateStore stateStore = new InMemoryStateStore();

        //TODO диспетчер принимает сервисы
        AddRecipeHandler addRecipeHandler = new AddRecipeHandler(stateStore, telegramBotSender, recipeService);
        CommandHandler commandHandler = new CommandHandler(stateStore, telegramBotSender);
        CallbackHandler callbackHandler = new CallbackHandler(stateStore, telegramBotSender, addRecipeHandler);
        TextHandler textHandler = new TextHandler(stateStore, telegramBotSender, addRecipeHandler);

        UpdateDispatcher dispatcher = new UpdateDispatcher(commandHandler, callbackHandler, textHandler);

        bot.setDispatcher(dispatcher);
    }
}
