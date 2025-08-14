package bot.telegram.recipes.chatbot.presentation;

import bot.telegram.recipes.chatbot.handlers.UpdateDispatcher;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {
    private final String username;
    private final String token;
    private UpdateDispatcher dispatcher;

    public Bot(String username, String token) {
        this.username = username;
        this.token = token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void setDispatcher(UpdateDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        dispatcher.route(update);
    }
}
