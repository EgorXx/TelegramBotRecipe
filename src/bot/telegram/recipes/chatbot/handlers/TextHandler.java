package bot.telegram.recipes.chatbot.handlers;

import bot.telegram.recipes.chatbot.presentation.TelegramBotSender;
import bot.telegram.recipes.chatbot.state.StateStore;
import bot.telegram.recipes.chatbot.state.UserSession;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TextHandler {
    private final StateStore stateStore;
    private final TelegramBotSender telegramBotSender;

    public TextHandler(StateStore stateStore, TelegramBotSender telegramBotSender) {
        this.stateStore = stateStore;
        this.telegramBotSender = telegramBotSender;
    }

    public void handleTextMessage(Update update, UserSession session) throws TelegramApiException {

    }
}
