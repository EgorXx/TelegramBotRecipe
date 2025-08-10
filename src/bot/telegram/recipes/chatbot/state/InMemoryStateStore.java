package bot.telegram.recipes.chatbot.state;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStateStore  implements StateStore {

    private Map<Long, UserSession> userSessions;

    public InMemoryStateStore() {
        userSessions = new ConcurrentHashMap<>();
    }

    @Override
    public UserSession get(Long chatId) {
        return userSessions.get(chatId);
    }

    @Override
    public void set(Long chatId, UserSession session) {
        userSessions.put(chatId, session);
    }

    @Override
    public void reset(Long chatId) {
        userSessions.remove(chatId);
    }

    @Override
    public void clear() {
        userSessions.clear();
    }
}
