package bot.telegram.recipes.repository.impl;

import bot.telegram.recipes.entities.Ingredient;
import bot.telegram.recipes.repository.IngredientRepositoryJdbc;

import java.util.List;
import java.util.Optional;

public class IngredientRepositoryJdbcImpl  implements IngredientRepositoryJdbc {

    @Override
    public void save(Ingredient ingredient) {

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
