package bot.telegram.recipes.util.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class JdbcConfig {
    public static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/telegram_bot_recipes");
        config.setUsername("postgres");
        config.setPassword("qwerty007");

        return new HikariDataSource(config);
    }
}
