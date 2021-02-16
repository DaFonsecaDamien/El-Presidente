package esgi.fr;

import java.util.List;

public class Event {
    List<Choice> choices;
    String name;

    public Event(List<Choice> choices, String name) {
        this.choices = choices;
        this.name = name;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public String getName() {
        return name;
    }
}
