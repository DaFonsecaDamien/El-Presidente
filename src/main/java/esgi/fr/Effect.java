package esgi.fr;

import java.util.HashMap;
import java.util.Map;

public class Effect {
    private String typeAction;
    private Map<String,Integer> actions;

    public Effect(String typeAction, Map<String, Integer> actions) {
        this.typeAction = typeAction;
        this.actions = actions;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public Map<String, Integer> getActions() {
        return actions;
    }
}
