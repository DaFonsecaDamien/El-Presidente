package esgi.fr;

import java.io.Serializable;
import java.util.Map;

public class Effect implements Serializable {
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
