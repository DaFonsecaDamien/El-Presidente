package esgi.fr;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable {

    String name;
    Season season;
    List<Choice> choices;

    public Event(List<Choice> choices, String name) {
        this.name = name;
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public String getName() {
        return name;
    }

    public Season getSeason(){
        return season;
    }
}
