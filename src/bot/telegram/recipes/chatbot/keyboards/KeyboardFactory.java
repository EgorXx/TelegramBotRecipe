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
}
