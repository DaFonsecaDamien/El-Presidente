package esgi.fr;

import java.util.List;
import java.util.Map;

public class Scenario {
    private List<Event> events;
    private String name;
    private String story;
    private int treasury;
    private int foodUnit;
    private int industryPercentage;
    private int agriculturePercentage;
    private List<Faction> factions;

    public Scenario(List<Event> events,String name,String story, int treasury, int foodUnit, int industryPercentage, int agriculturePercentage, List<Faction> factions) {
        this.events = events;
        this.name = name;
        this.story = story;
        this.treasury = treasury;
        this.foodUnit = foodUnit;
        this.industryPercentage = industryPercentage;
        this.agriculturePercentage = agriculturePercentage;
        this.factions = factions;
    }


    public List<Event> getEvents() {
        return events;
    }

    public String getName() { return name; }

    public String getStory() { return story; }

    public int getTreasury() {
        return treasury;
    }

    public int getIndustryPercentage() {
        return industryPercentage;
    }

    public int getAgriculturePercentage() {
        return agriculturePercentage;
    }

    public int getFoodUnit() {
        return foodUnit;
    }

    public List<Faction> getFactions() { return factions; }

    public void setTreasury(int treasury) {
        this.treasury = treasury;
    }
}
