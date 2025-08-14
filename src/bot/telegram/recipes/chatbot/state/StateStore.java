package bot.telegram.recipes.chatbot.state;

public interface StateStore {
    UserSession get(Long chatId);
    void set(Long chatId, UserSession session);
    void reset(Long chatId);
    void clear();
    boolean contains(Long chatId);
}
