package bot.telegram.recipes.repository;

import bot.telegram.recipes.entities.Ingredient;
import bot.telegram.recipes.entities.TypeOfDish;

import java.util.List;
import java.util.Optional;

public interface IngredientRepositoryJdbc {
    void save(Ingredient ingredient);

    List<Ingredient> findAll();

    Optional<Ingredient> findById(Long id);

    List<Ingredient> findByRecipeId(Integer recipeId);

    void deleteById(Integer id);

    void deleteByRecipeId(Integer recipeId);
}
