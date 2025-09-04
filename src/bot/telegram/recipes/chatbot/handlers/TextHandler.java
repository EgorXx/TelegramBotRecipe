package bot.telegram.recipes.chatbot.handlers;

import bot.telegram.recipes.chatbot.presentation.TelegramBotSender;
import bot.telegram.recipes.chatbot.state.Stage;
import bot.telegram.recipes.chatbot.state.StateStore;
import bot.telegram.recipes.chatbot.state.UserSession;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TextHandler {
    private final StateStore stateStore;
    private final TelegramBotSender telegramBotSender;
    private final AddRecipeHandler addRecipeHandler;

    public TextHandler(StateStore stateStore, TelegramBotSender telegramBotSender, AddRecipeHandler addRecipeHandler) {
        this.stateStore = stateStore;
        this.telegramBotSender = telegramBotSender;
        this.addRecipeHandler = addRecipeHandler;
    }

    public void handleTextMessage(Message message) {
        Long chatId = message.getChatId();
        UserSession userSession = stateStore.get(chatId);
        Stage stage = userSession.getStage();

        if (isAddRecipeTextStage(stage)) {
            addRecipeHandler.addText(message);
        }
    }

    public Boolean isAddRecipeTextStage(Stage stage) {
        return stage == Stage.ADD_NAME || stage == Stage.ADD_ING_NAME || stage == Stage.ADD_ING_COUNT
                || stage == Stage.ADD_STEPS || stage == Stage.EDIT_TITLE || stage == Stage.EDIT_STEPS
                || stage == Stage.EDIT_ING || stage == Stage.EDIT_ING_TITLE || stage == Stage.EDIT_ING_COUNT;
    }
}
