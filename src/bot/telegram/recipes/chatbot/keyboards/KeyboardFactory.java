package bot.telegram.recipes.chatbot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class KeyboardFactory {
    public static InlineKeyboardMarkup mainMenu() {
        InlineKeyboardButton addRecipe = InlineKeyboardButton.builder()
                .text("Добавить рецепт").callbackData("ADD:START")
                .build();

        InlineKeyboardButton chooseRecipe = InlineKeyboardButton.builder()
                .text("Выбрать рецепт").callbackData("ADDchoose_recipe")
                .build();

        InlineKeyboardButton randomRecipe = InlineKeyboardButton.builder()
                .text("Случайный рецепт").callbackData("random_recipe")
                .build();

        InlineKeyboardButton addTimeForRecipe = InlineKeyboardButton.builder()
                .text("Добавить время для рецепта").callbackData("add_time_for_recipe")
                .build();

        InlineKeyboardMarkup keyboardForMenu = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(addRecipe))
                .keyboardRow(List.of(chooseRecipe))
                .keyboardRow(List.of(randomRecipe))
                .keyboardRow(List.of(addTimeForRecipe))
                .build();

        return keyboardForMenu;
    }

    public static InlineKeyboardMarkup backToMainMenu() {
        InlineKeyboardButton back = InlineKeyboardButton.builder()
                .text("Назад").callbackData("ADD:MAIN:MENU")
                .build();

        InlineKeyboardMarkup keyboardbackToMenu = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(back))
                .build();

        return keyboardbackToMenu;
    }

    public static InlineKeyboardMarkup chooseTypeOfDish() {
        InlineKeyboardButton main = InlineKeyboardButton.builder()
                .text("Самостоятельное блюдо").callbackData("ADD:TYPE:MAIN")
                .build();

        InlineKeyboardButton meat = InlineKeyboardButton.builder()
                .text("Мясное").callbackData("ADD:TYPE:MEAT")
                .build();

        InlineKeyboardButton side = InlineKeyboardButton.builder()
                .text("Гарнир").callbackData("ADD:TYPE:SIDE")
                .build();

        InlineKeyboardButton salad = InlineKeyboardButton.builder()
                .text("Салат").callbackData("ADD:TYPE:SALAD")
                .build();

        InlineKeyboardButton snack = InlineKeyboardButton.builder()
                .text("Закуска").callbackData("ADD:TYPE:SNACK")
                .build();

        InlineKeyboardMarkup keyboardForChooseTypeOfDish = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(main))
                .keyboardRow(List.of(meat))
                .keyboardRow(List.of(side))
                .keyboardRow(List.of(salad))
                .keyboardRow(List.of(snack))
                .build();

        return keyboardForChooseTypeOfDish;
    }

    public static InlineKeyboardMarkup endForAddIngredient() {
        InlineKeyboardButton ready = InlineKeyboardButton.builder()
                .text("Готово").callbackData("ADD:ING:DONE")
                .build();


        InlineKeyboardMarkup keyboardEndForAddIngredient = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(ready))
                .build();

        return keyboardEndForAddIngredient;
    }

    public static InlineKeyboardMarkup chooseUnitOfIngredient() {
        InlineKeyboardButton gram = InlineKeyboardButton.builder()
                .text("Граммы").callbackData("ADD:UNIT:GRAM")
                .build();

        InlineKeyboardButton milliliter = InlineKeyboardButton.builder()
                .text("Миллилитры").callbackData("ADD:UNIT:MILLILITER")
                .build();

        InlineKeyboardButton cup = InlineKeyboardButton.builder()
                .text("Стаканы").callbackData("ADD:UNIT:CUP")
                .build();

        InlineKeyboardButton tablespoon = InlineKeyboardButton.builder()
                .text("Столовые ложки").callbackData("ADD:UNIT:TABLESPOON")
                .build();

        InlineKeyboardButton teaspoon = InlineKeyboardButton.builder()
                .text("Чайные ложки").callbackData("ADD:UNIT:TEASPOON")
                .build();

        InlineKeyboardMarkup keyboardForChooseUnitOfIngredient = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(gram))
                .keyboardRow(List.of(milliliter))
                .keyboardRow(List.of(cup))
                .keyboardRow(List.of(tablespoon))
                .keyboardRow(List.of(teaspoon))
                .build();

        return keyboardForChooseUnitOfIngredient;
    }

    public static InlineKeyboardMarkup doneOrRewiewRecipe() {
        InlineKeyboardButton done = InlineKeyboardButton.builder()
                .text("Готово").callbackData("ADD:RECIPE:SAVE")
                .build();

        InlineKeyboardButton rewiew = InlineKeyboardButton.builder()
                .text("Редактирвоать").callbackData("ADD:RECIPE:REWIEW")
                .build();

        InlineKeyboardMarkup doneOrRewiew = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(done))
                .keyboardRow(List.of(rewiew))
                .build();

        return doneOrRewiew;
    }

    public static InlineKeyboardMarkup selectFieldForEditRecipe() {
        InlineKeyboardButton title = InlineKeyboardButton.builder()
                .text("Название блюда").callbackData("EDIT:FIELD:SELECT:TITLE")
                .build();

        InlineKeyboardButton type = InlineKeyboardButton.builder()
                .text("Тип блюда").callbackData("EDIT:FIELD:SELECT:TYPE")
                .build();

        InlineKeyboardButton ingredients = InlineKeyboardButton.builder()
                .text("Список ингредиентов").callbackData("EDIT:FIELD:SELECT:INGREDIENTS")
                .build();

        InlineKeyboardButton steps = InlineKeyboardButton.builder()
                .text("Пошаговый рецепт блюда").callbackData("EDIT:FIELD:SELECT:STEPS")
                .build();

        InlineKeyboardButton done = InlineKeyboardButton.builder()
                .text("Завершить редактирование").callbackData("EDIT:DONE")
                .build();

        InlineKeyboardMarkup fieldForEditRecipe = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(title))
                .keyboardRow(List.of(type))
                .keyboardRow(List.of(ingredients))
                .keyboardRow(List.of(steps))
                .keyboardRow(List.of(done))
                .build();

        return fieldForEditRecipe;
    }

    public static InlineKeyboardMarkup selectFieldForEditIngredients() {
        InlineKeyboardButton change = InlineKeyboardButton.builder()
                .text("Изменить ингредиент").callbackData("EDIT:ING:LIST:CHANGE")
                .build();

        InlineKeyboardButton add = InlineKeyboardButton.builder()
                .text("Добавить ингредиент").callbackData("EDIT:ING:LIST:ADD")
                .build();

        InlineKeyboardButton remove = InlineKeyboardButton.builder()
                .text("Удалить ингредиент").callbackData("EDIT:ING:LIST:REMOVE")
                .build();

        InlineKeyboardButton ready = InlineKeyboardButton.builder()
                .text("Готово").callbackData("EDIT:ING:LIST:READY")
                .build();

        InlineKeyboardMarkup fieldForEditIngredients= InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(change))
                .keyboardRow(List.of(add))
                .keyboardRow(List.of(remove))
                .keyboardRow(List.of(ready))
                .build();

        return fieldForEditIngredients;
    }

    public static InlineKeyboardMarkup selectFieldForEditIng() {
        InlineKeyboardButton title = InlineKeyboardButton.builder()
                .text("Название").callbackData("EDIT:ING:TITLE")
                .build();

        InlineKeyboardButton unit = InlineKeyboardButton.builder()
                .text("Единица измерения").callbackData("EDIT:ING:UNIT")
                .build();

        InlineKeyboardButton count = InlineKeyboardButton.builder()
                .text("Количество").callbackData("EDIT:ING:COUNT")
                .build();

        InlineKeyboardButton ready = InlineKeyboardButton.builder()
                .text("Готово").callbackData("EDIT:ING:READY")
                .build();

        InlineKeyboardMarkup fieldForEditIng= InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(title))
                .keyboardRow(List.of(unit))
                .keyboardRow(List.of(count))
                .keyboardRow(List.of(ready))
                .build();

        return fieldForEditIng;
    }

    public static InlineKeyboardMarkup editIngEndForAdd() {
        InlineKeyboardButton ready = InlineKeyboardButton.builder()
                .text("Готово").callbackData("EDIT:ING:ADD:DONE")
                .build();


        InlineKeyboardMarkup keyboardEndForAddIngredient = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(ready))
                .build();

        return keyboardEndForAddIngredient;
    }

    public static InlineKeyboardMarkup editIngStopEnterNumber() {
        InlineKeyboardButton back = InlineKeyboardButton.builder()
                .text("Назад").callbackData("EDIT:ING:DONE")
                .build();

        InlineKeyboardMarkup keyboardEndForEnterNumberIngredient = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(back))
                .build();

        return keyboardEndForEnterNumberIngredient;
    }
}
