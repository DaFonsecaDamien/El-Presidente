package esgi.fr.utilities.Event;

import java.io.Serializable;
import java.util.List;

public class Choice implements Serializable {
    private final List<Effect> effects;
    private final String name;
    private final Integer relatedEvents;

    public Choice(List<Effect> effects, String name, Integer relatedEvents) {
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

    public Integer getRelatedEvents() {
        return relatedEvents;
    }

}
