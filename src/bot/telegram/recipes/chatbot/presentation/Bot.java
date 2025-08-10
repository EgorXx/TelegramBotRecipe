package bot.telegram.recipes.chatbot.presentation;

import bot.telegram.recipes.chatbot.handlers.UpdateDispatcher;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {
    private final String username;
    private final String token;
    private UpdateDispatcher dispatcher;

    public Bot(String username, String token, UpdateDispatcher dispatcher) {
        this.username = username;
        this.token = token;
        this.dispatcher = dispatcher;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //TODO dispatcher.route(update);
        System.out.println(update);
    }
}
