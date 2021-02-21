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
    private ListFaction listFactions;

    public Scenario(List<Event> events,String name,String story, int treasury, int foodUnit, int industryPercentage, int agriculturePercentage, ListFaction factions) {
        this.events = events;
        this.name = name;
        this.story = story;
        this.treasury = treasury;
        this.foodUnit = foodUnit;
        this.industryPercentage = industryPercentage;
        this.agriculturePercentage = agriculturePercentage;
        this.listFactions = factions;
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

    public ListFaction getListFactions() { return listFactions; }

    public void setTreasury(int treasury) {
        if(this.treasury+treasury<0){
            this.treasury = 0;
            return;
        }
        this.treasury = treasury;
    }

    public void setFoodUnit(int foodUnit) { this.foodUnit = foodUnit; }

    public void setIndustryPercentage(int industryPercentage) { this.industryPercentage = industryPercentage; }

    public void setAgriculturePercentage(int agriculturePercentage) { this.agriculturePercentage = agriculturePercentage; }
}
