package bot.telegram.recipes.chatbot.handlers;

import bot.telegram.recipes.chatbot.formatting.MessageFormatter;
import bot.telegram.recipes.chatbot.keyboards.KeyboardFactory;
import bot.telegram.recipes.chatbot.presentation.TelegramBotSender;
import bot.telegram.recipes.chatbot.state.StateStore;
import bot.telegram.recipes.chatbot.state.UserSession;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class CallbackHandler {
    private final StateStore stateStore;
    private final TelegramBotSender telegramBotSender;
    private final AddRecipeHandler addRecipeHandler;


    public CallbackHandler(StateStore stateStore, TelegramBotSender telegramBotSender, AddRecipeHandler addRecipeHandler) {
        this.stateStore = stateStore;
        this.telegramBotSender = telegramBotSender;
        this.addRecipeHandler = addRecipeHandler;
    }

    public void handleCallback(CallbackQuery callbackQuery) {
        String id = callbackQuery.getId();
        telegramBotSender.execute(new AnswerCallbackQuery(id));

        if (callbackQuery.getData().startsWith("ADD") ||
                callbackQuery.getData().startsWith("EDIT") ||
                callbackQuery.getData().startsWith("REMOVE")) {
            addRecipeHandler.addCallback(callbackQuery);
        }
    }
}
