package bot.telegram.recipes.repository.impl;

import bot.telegram.recipes.cache.LookupDataCache;
import bot.telegram.recipes.entities.Ingredient;
import bot.telegram.recipes.repository.IngredientRepositoryJdbc;
import bot.telegram.recipes.util.Util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class IngredientRepositoryJdbcImpl  implements IngredientRepositoryJdbc {

    private DataSource ds;

    private String SQL_INSERT = "INSERT INTO ingredient (title, quanity, unit_id, recipe_id) VALUES (?, ?, ?, ?)";

    public IngredientRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public void save(Ingredient ingredient, int recipe_id) {

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {

            Integer unit_id = LookupDataCache.getUnitId(Util.unitToString(ingredient.getUnit()));
            if (unit_id == null) {
                throw new SQLException("Could not find ID for Unit: " + Util.unitToString(ingredient.getUnit()));
            }

            preparedStatement.setString(1, ingredient.getTitle());
            preparedStatement.setDouble(2, ingredient.getCount());
            preparedStatement.setInt(3, unit_id);
            preparedStatement.setInt(4, recipe_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("SQLException while save the ingredient", e);
        }
    }

    @Override
    public List<Ingredient> findAll() {
        return List.of();
    }

    @Override
    public Optional<Ingredient> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Ingredient> findByRecipeId(Integer recipeId) {
        return List.of();
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void deleteByRecipeId(Integer recipeId) {

    }
}
