package bot.telegram.recipes.chatbot.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class BotProperties {
    private final String username;
    private final String token;
    private static final String PROPERTIES_FILE = "application.properties";

    private BotProperties(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public static BotProperties load() {
        Properties prop = new Properties();

        try (InputStream is = new FileInputStream(PROPERTIES_FILE)) {
            prop.load(is);
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось найти файл с свойствами");
        }

        String username = prop.getProperty("bot.username");
        String token = prop.getProperty("bot.token");

        if (username == null || token == null) {
            throw new IllegalStateException("В файле: " +  PROPERTIES_FILE + " должны быть поля bot.username и bot.token");
        }

        return new BotProperties(username, token);
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
