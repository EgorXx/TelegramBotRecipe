package bot.telegram.recipes.chatbot.formatting;

import bot.telegram.recipes.entities.Unit;

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

    public static String getUnitFromEnum(Unit unit) {
        switch (unit) {
            case GRAM : return "граммы";
            case MILLILITER : return "миллилитры";
            case CUP : return "стаканы";
            case TABLESPOON : return "столовые ложки";
            case TEASPOON : return "чайные ложки";
            default: return null;
        }
    }
}
