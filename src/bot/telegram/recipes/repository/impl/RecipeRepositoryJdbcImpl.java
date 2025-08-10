package bot.telegram.recipes.repository.impl;

import bot.telegram.recipes.entities.Recipe;
import bot.telegram.recipes.entities.TypeOfDish;
import bot.telegram.recipes.repository.RecipeRepositoryJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RecipeRepositoryJdbcImpl implements RecipeRepositoryJdbc {

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

    @Override
    public void save(Recipe recipe) {

    }

    @Override
    public Optional<Recipe> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Recipe> findAll() {
        return List.of();
    }

    @Override
    public void update(Recipe recipe) {

    }

    @Override
    public void delete(Recipe recipe) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void findRandom() {

    }

    @Override
    public void findRandomByType(TypeOfDish typeOfDish) {

    }
}
