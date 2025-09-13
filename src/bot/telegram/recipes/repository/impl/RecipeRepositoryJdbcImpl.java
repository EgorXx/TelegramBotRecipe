package bot.telegram.recipes.repository.impl;

import bot.telegram.recipes.cache.LookupDataCache;
import bot.telegram.recipes.entities.Recipe;
import bot.telegram.recipes.entities.TypeOfDish;
import bot.telegram.recipes.repository.RecipeRepositoryJdbc;
import bot.telegram.recipes.util.Util;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class RecipeRepositoryJdbcImpl implements RecipeRepositoryJdbc {

    private DataSource ds;

    private String SQL_INSERT = "INSERT INTO recipe (type_id, title, steps) VALUES (?, ?, ?)";

    public RecipeRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public int save(Recipe recipe) {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            Integer type_id = LookupDataCache.getTypeOfDishId(Util.typeOfDishToString(recipe.getType()));
            if (type_id == null) {
                throw new SQLException("Could not find ID for TypeOfDish: " + recipe.getType().name());
            }

            preparedStatement.setInt(1, type_id);
            preparedStatement.setString(2, recipe.getTitle());
            preparedStatement.setString(3, recipe.getSteps());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating recipe failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating recipe failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("", e);
        }
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
