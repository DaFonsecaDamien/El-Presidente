package esgi.fr;

import java.util.List;

public class Event {

    String name;
    Season season;
    List<Choice> choices;

    public Event(List<Choice> choices, String name) {
        this.name = name;
        this.choices = choices;
//        this.season = season;
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
