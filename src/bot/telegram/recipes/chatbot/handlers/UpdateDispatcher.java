package bot.telegram.recipes.chatbot.handlers;

import bot.telegram.recipes.chatbot.config.BotProperties;
import bot.telegram.recipes.chatbot.presentation.Bot;
import bot.telegram.recipes.chatbot.presentation.TelegramBotSender;
import bot.telegram.recipes.chatbot.state.StateStore;
import bot.telegram.recipes.chatbot.state.UserSession;
import org.telegram.telegrambots.meta.api.objects.Update;


public class UpdateDispatcher {
    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;
    private final TextHandler textHandler;

    public UpdateDispatcher(CommandHandler commandHandler, CallbackHandler callbackHandler,
                            TextHandler textHandler) {
        this.commandHandler = commandHandler;
        this.callbackHandler = callbackHandler;
        this.textHandler = textHandler;
    }

    public void route(Update update) {
        if (update.hasMessage() && update.getMessage().isCommand()) {
            commandHandler.handleCommand(update.getMessage());
//        } else if (update.hasCallbackQuery()) {
//            callbackHandler.handleCallback(update.getCallbackQuery());
//        } else if (update.hasMessage() && update.getMessage().hasText()) {
//            textHandler.handleTextMessage(update.getMessage());
        } else {
            // TODO реализовать я не понимаю вашего сообщения (должно сообщене для помощи)
        }
    }

}
