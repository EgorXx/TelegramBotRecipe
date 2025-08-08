package bot.telegram.recipes.entities;

import java.util.List;

public class Recipe {
    private Integer id;
    private TypeOfDish type;
    private String title;
    private String steps;
    private Double cookingTime;
    private List<Ingredient> ingredients;

    public Recipe(Integer id, TypeOfDish type, String title, String steps, Double time, List<Ingredient> ingredients) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.steps = steps;
        this.cookingTime = time;
        this.ingredients = ingredients;
    }

    public Recipe() {}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(TypeOfDish type) {
        this.type = type;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCookingTime(Double cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getId() {
        return id;
    }

    public TypeOfDish getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Double getCookingTime() {
        return cookingTime;
    }

    public String getSteps() {
        return steps;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
