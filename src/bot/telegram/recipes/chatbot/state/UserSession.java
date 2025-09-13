package bot.telegram.recipes.chatbot.state;

import bot.telegram.recipes.entities.Ingredient;
import bot.telegram.recipes.entities.Recipe;
import bot.telegram.recipes.entities.TypeOfDish;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

public class UserSession {
    private Stage stage;
    private TypeOfDish selectedDish;
    private List<Long> lastIdList;
    private Integer lastIndex;
    private Recipe draftRecipe;
    private Ingredient tempIngredient;
    private List<Message> removeMessages;

    public static UserSession defaultSession() {
        UserSession session = new UserSession();
        session.stage = Stage.IDLE;
        session.lastIdList = new ArrayList<>();
        session.removeMessages = new ArrayList<>();

        Recipe recipe = new Recipe();
        recipe.setIngredients(new ArrayList<>());

        session.draftRecipe = recipe;
        session.tempIngredient = new Ingredient();

        return session;
    }

    public Stage getStage() {
        return stage;
    }

    public TypeOfDish getSelectedDish() {
        return selectedDish;
    }

    public List<Long> getLastIdList() {
        return lastIdList;
    }

    public Recipe getDarftRecipe() {
        return draftRecipe;
    }

    public Ingredient getTempIngredient() {
        return tempIngredient;
    }

    public Integer getLastIndex() {return lastIndex;}

    public List<Message> getRemoveMessages() {return removeMessages;}

    public void setLastIndex(Integer lastIndex) {this.lastIndex = lastIndex;}

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSelectedDish(TypeOfDish selectedDish) {
        this.selectedDish = selectedDish;
    }

    public void setLastIdList(List<Long> lastIdList) {
        this.lastIdList = lastIdList;
    }

    public void setDarftRecipe(Recipe darftRecipe) {
        this.draftRecipe = darftRecipe;
    }

    public void setTempIngredient(Ingredient tempIngredient) {this.tempIngredient = tempIngredient;}

    public void setRemoveMessages(List<Message> removeMessages) {this.removeMessages = removeMessages;}
}
