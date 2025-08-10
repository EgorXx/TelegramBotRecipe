package bot.telegram.recipes.chatbot.state;

public enum Stage {
    IDLE,
    WAITING_TYPE,
    WAITING_NUMBER,
    WAITING_TEXT,
    WAITING_TIME
}
