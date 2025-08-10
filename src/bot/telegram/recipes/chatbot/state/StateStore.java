package bot.telegram.recipes.chatbot.state;

interface StateStore {
    UserSession get(Long chatId);
    void set(Long chatId, UserSession session);
    void reset(Long chatId);
    void clear();
}
