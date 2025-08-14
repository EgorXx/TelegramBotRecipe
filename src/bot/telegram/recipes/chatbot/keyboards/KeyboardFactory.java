package bot.telegram.recipes.chatbot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class KeyboardFactory {
    public static InlineKeyboardMarkup mainMenu() {
        InlineKeyboardButton addRecipe = InlineKeyboardButton.builder()
                .text("Добавить рецепт").callbackData("add_recipe")
                .build();

        InlineKeyboardButton chooseRecipe = InlineKeyboardButton.builder()
                .text("Выбрать рецепт").callbackData("choose_recipe")
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
        ;

        return keyboardForMenu;
    }
}
