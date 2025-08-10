package bot.telegram.recipes.app;

import bot.telegram.recipes.chatbot.config.BotProperties;
import bot.telegram.recipes.chatbot.handlers.UpdateDispatcher;
import bot.telegram.recipes.chatbot.presentation.Bot;
import bot.telegram.recipes.repository.impl.RecipeRepositoryJdbcImpl;
import bot.telegram.recipes.util.jdbc.JdbcConfig;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        //TODO репозитории сервисы (сервисы принимают репозитории)
        RecipeRepositoryJdbcImpl recipeRepositoryJdbcImpl = new RecipeRepositoryJdbcImpl(JdbcConfig.getDataSource());

        //TODO диспетчер принимает сервисы
        UpdateDispatcher dispatcher = new UpdateDispatcher();


        BotProperties prop = BotProperties.load();
        Bot bot  = new Bot(prop.getUsername(), prop.getToken(), dispatcher);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        botsApi.registerBot(bot);
    }
}
