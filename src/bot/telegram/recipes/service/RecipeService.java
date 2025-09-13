package bot.telegram.recipes.service;

import bot.telegram.recipes.entities.Ingredient;
import bot.telegram.recipes.entities.Recipe;
import bot.telegram.recipes.repository.IngredientRepositoryJdbc;
import bot.telegram.recipes.repository.RecipeRepositoryJdbc;

public class RecipeService {
    private final RecipeRepositoryJdbc recipeRepository;
    private final IngredientRepositoryJdbc ingredientRepository;

    public RecipeService(RecipeRepositoryJdbc recipeRepository, IngredientRepositoryJdbc ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public void saveRecipe(Recipe recipe) {
        int recipeId = recipeRepository.save(recipe);

        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredientRepository.save(ingredient, recipeId);
        }
    }
}
