package esgi.fr;

import java.io.Serializable;
import java.util.List;

public class Choice implements Serializable {
    private List<Effect> effects;
    private String name;
    private List<Event> relatedEvents;

    public Choice(List<Effect> effects, String name, List<Event> relatedEvents) {
        this.effects = effects;
        this.name = name;
        this.relatedEvents = relatedEvents;
    }

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
