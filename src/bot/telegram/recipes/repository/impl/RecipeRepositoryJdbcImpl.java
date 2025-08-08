package bot.telegram.recipes.repository.impl;

import javax.sql.DataSource;
import java.sql.SQLException;

public class RecipeRepositoryJdbcImpl {

    private DataSource ds;

    public RecipeRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }

    public void printConnection() {
        try {
            System.out.println(ds.getConnection());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
