package bot.telegram.recipes.chatbot.state;

public enum Stage {
    IDLE,
    WAITING_TYPE,
    WAITING_NUMBER,
    ADD_TYPE,
    ADD_NAME,
    ADD_ING_NAME,
    ADD_ING_COUNT,
    ADD_ING_UNIT,
    ADD_STEPS,
    ADD_REWIEW,
    EDIT_FIELD_SELECT,
    EGIT_ING_LIST,
    EDIT_ING_EDIT
}
