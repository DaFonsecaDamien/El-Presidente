package esgi.fr.utilities.Event;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable {

    int id;
    String name;
    List<Choice> choices;

    public Event(int id,List<Choice> choices, String name) {
        this.id = id;
        this.name = name;
        this.choices = choices;
    }

    public int getId() {
        return id;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public String getName() {
        return name;
    }
}
