package esgi.fr;

import java.util.List;

public class Choice {
    private List<Effect> effects;
    private String name;
    private List<Event> relatedEvents;
    //consequence


    public List<Effect> getEffects() {
        return effects;
    }

    public String getName() {
        return name;
    }

    public List<Event> getRelatedEvents() {
        return relatedEvents;
    }
}
