package bot.telegram.recipes.repository;

import bot.telegram.recipes.entities.Recipe;
import bot.telegram.recipes.entities.TypeOfDish;

import java.util.List;
import java.util.Optional;

public interface RecipeRepositoryJdbc {
    int save(Recipe recipe);

    Optional<Recipe> findById(Integer id);

    List<Recipe> findAll();

    void update(Recipe recipe);

    void delete(Recipe recipe);

    void deleteById(Integer id);

    void findRandom();

    void findRandomByType(TypeOfDish typeOfDish);


}
