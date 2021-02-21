package esgi.fr;

import java.util.HashMap;

public class Effect {
    private String typeAction;
    private HashMap<String,Integer> actions;

    public Effect(String typeAction, HashMap<String, Integer> actions) {
        this.typeAction = typeAction;
        this.actions = actions;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public HashMap<String, Integer> getActions() {
        return actions;
    }
}
