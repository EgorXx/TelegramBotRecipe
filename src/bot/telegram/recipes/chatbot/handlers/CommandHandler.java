package bot.telegram.recipes.chatbot.handlers;

import bot.telegram.recipes.chatbot.formatting.MessageFormatter;
import bot.telegram.recipes.chatbot.keyboards.KeyboardFactory;
import bot.telegram.recipes.chatbot.presentation.TelegramBotSender;
import bot.telegram.recipes.chatbot.state.StateStore;
import bot.telegram.recipes.chatbot.state.UserSession;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class CommandHandler {
    private final StateStore stateStore;
    private final TelegramBotSender telegramBotSender;

    public CommandHandler(StateStore stateStore, TelegramBotSender telegramBotSender) {
        this.stateStore = stateStore;
        this.telegramBotSender = telegramBotSender;
    }

    public void handleCommand(Message message) {
        switch (message.getText()) {
            case "/start" -> handleStart(message);
        }
    }

    public void handleStart(Message message) {
        Long chatId = message.getChatId();
        stateStore.reset(chatId);

        telegramBotSender.sendMessageWithParseMode(chatId, MessageFormatter.menuTitle(),
                KeyboardFactory.mainMenu(), "HTML");
    }
}
