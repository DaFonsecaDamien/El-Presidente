package esgi.fr;

public class Effect {
    private String typeAction;
    private String action;
    private int value;

    public Effect(String typeAction, String action, int value) {
        this.typeAction = typeAction;
        this.action = action;
        this.value = value;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public String getAction() {
        return action;
    }

    public int getValue() {
        return value;
    }
}
