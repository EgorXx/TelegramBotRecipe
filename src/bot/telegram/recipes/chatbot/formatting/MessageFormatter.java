package bot.telegram.recipes.chatbot.formatting;

import bot.telegram.recipes.entities.Ingredient;
import bot.telegram.recipes.entities.Recipe;
import bot.telegram.recipes.entities.TypeOfDish;
import bot.telegram.recipes.entities.Unit;

import java.util.List;

public class MessageFormatter {
    public static String menuTitle() {
        return "<b>Menu</b>";
    }

    public static String startAddRecipe() {
        return "<b>Вы начали добавление нового рецепта. " +
                "Не бойтесь, если вы допустили ошибку во время добавления рецепта, " +
                "указав неправильные данные или недописали рецепт и тому подобное, " +
                "в конце у вас будет возможность отредактировать ваш рецепт перед его добавлением. " +
                "Поэтому при ошибке, просто просто продолжайте добавлять ваш рецепт. " +
                "У вас все получится :)</b>";
    }

    public static String selectTypeOfDish() {
        return "<b>Выберите тип блюда, рецепт которого хотите добавить</b>";
    }

    public static String addNameForDish() {
        return "<b>Введите название блюда, рецепт которого хотите добавить</b>";
    }

    public static String addNameForIngredient() {
        return "<b>Введите название ингридиента, который хотите добавить в рецепт. Или нажмите \"Готово\"</b>";
    }

    public static String addUnitForIngredient() {
        return "<b>Выберите единицу измерения для количества ингредиента</b>";
    }

    public static String addCountForIngredient() {
        return "<b>Введите количество ингридиента, с учетом раннее выбранной единицы измерения</b>";
    }

    public static String addStepsForRecipe() {
        return "<b>Введите пожалуйста пошаговый рецепт приготовления блюда</b>";
    }

    public static String editTypeOfDish(String typeOfDish) {
        return "Cтарый тип блюда: " +  typeOfDish
                + "\n\n" + "<b>Выберите пожалуйста новый тип блюда</b>";
    }

    public static String UnitToString(Unit unit) {
        switch (unit) {
            case GRAM : return "граммы";
            case MILLILITER : return "миллилитры";
            case CUP : return "стаканы";
            case TABLESPOON : return "столовые ложки";
            case TEASPOON : return "чайные ложки";
            default: return null;
        }
    }

    public static String startEditRecipe() {
        return "\n------------------------------------\n" +
                "Вы начали редактирование рецепта.\n" +
                "Выберите пожалуйста поле, которое хотите редактировать.\n" +
                "------------------------------------";
    }

    public static String editTitleRecipe(String title) {
        return "Cтарое название: " + title
                + "\n" + "Введите пожалуйста новое название блюда";
    }

    public static String editStepsRecipe(String steps) {
        return "Cтарый пошаговый рецепт: " + steps
                + "\n" + "Введите пожалуйста новый пошаговый рецепт";
    }

    public static String editDone() {
        return "Редактирование прошло успешно!";
    }

    public static String editIngList(List<Ingredient> ingredients) {
        return "Список ингредиентов:\n"
                + ingredientToString(ingredients)
                + "\n\n" + "Выберите действие, которое хотите совершить над списком ингрединтов";
    }

    public static String changeIng(List<Ingredient> ingredients) {
        return "Список ингредиентов:\n"
                + ingredientToString(ingredients)
                + "\n\n" + "Введите номер ингредиента, который хотите отредактировать";
    }

    public static String selectFieldForEditIng(Ingredient ingredient, Integer numberOfIng) {
        String titleIngredient = ingredient.getTitle();
        String unit = UnitToString(ingredient.getUnit());
        String count = ingredient.getCount() + "";

        return numberOfIng + ". " + titleIngredient + " / " + unit + " / " + count +
                "\n\n" + "Выберите поле, которое хотите изменить у данного ингредиента";
    }

    public static String RecipeToString(Recipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredients();
        String titleRecipe = recipe.getTitle();
        String typeOfDish = typeOfDishToString(recipe.getType());
        String steps = recipe.getSteps();

        StringBuilder recipeString = new StringBuilder();

        recipeString.append("<b>Название: </b>");
        recipeString.append(titleRecipe);
        recipeString.append("\n");

        recipeString.append("<b>Тип блюда: </b>");
        recipeString.append(typeOfDish);
        recipeString.append("\n\n");

        recipeString.append("<b>Список ингредиентов:\n</b>");

        recipeString.append(ingredientToString(ingredients));

        recipeString.append("\n");

        recipeString.append("<b>Пошаговый рецепт блюда:\n</b>");
        recipeString.append(steps);

        return recipeString.toString();
    }

    public static String editIngTitle(Ingredient ingredient, Integer numberOfIng) {
        String titleIngredient = ingredient.getTitle();
        String unit = UnitToString(ingredient.getUnit());
        String count = ingredient.getCount() + "";

        return numberOfIng + ". " + titleIngredient + " / " + unit + " / " + count +
                "\n\n" + "Введите новое название ингридиента";
    }

    public static String editIngUnit(Ingredient ingredient, Integer numberOfIng) {
        String titleIngredient = ingredient.getTitle();
        String unit = UnitToString(ingredient.getUnit());
        String count = ingredient.getCount() + "";

        return numberOfIng + ". " + titleIngredient + " / " + unit + " / " + count +
                "\n\n" + "Выберите новую единицу измерения ингредиента";
    }

    public static String editIngCount(Ingredient ingredient, Integer numberOfIng) {
        String titleIngredient = ingredient.getTitle();
        String unit = UnitToString(ingredient.getUnit());
        String count = ingredient.getCount() + "";

        return numberOfIng + ". " + titleIngredient + " / " + unit + " / " + count +
                "\n\n" + "Введите новое количество ингредента";
    }

    public static String ingredientToString(List<Ingredient> ingredients) {
        StringBuilder ingredientsString = new StringBuilder();

        for (int i = 0; i < ingredients.size(); i++) {
            String titleIngredient = ingredients.get(i).getTitle();
            String unit = UnitToString(ingredients.get(i).getUnit());
            String count = ingredients.get(i).getCount() + "";
            ingredientsString.append(i + 1 + ". ");
            ingredientsString.append(titleIngredient);
            ingredientsString.append(" / ");
            ingredientsString.append(unit);
            ingredientsString.append(" (");
            ingredientsString.append(count);
            ingredientsString.append(")\n");
        }

        return ingredientsString.toString();
    }

    public static String typeOfDishToString(TypeOfDish typeOfDish) {
        switch (typeOfDish) {
            case MAIN : return "Самостоятельное блюдо";
            case MEAT : return "Мясное";
            case SIDE : return "Гарнир";
            case SALAD : return "Салат";
            case SNACK : return "Закуска";
            default : return null;
        }
    }
}
