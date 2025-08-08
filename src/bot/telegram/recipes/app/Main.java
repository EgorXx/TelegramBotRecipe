package bot.telegram.recipes.app;

import bot.telegram.recipes.repository.impl.RecipeRepositoryJdbcImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Main {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/telegram_bot_recipes");
        config.setUsername("postgres");
        config.setPassword("qwerty007");

        HikariDataSource ds = new HikariDataSource(config);
        RecipeRepositoryJdbcImpl recipeRepositoryJdbcImpl = new RecipeRepositoryJdbcImpl(ds);

        recipeRepositoryJdbcImpl.printConnection();
    }
}
