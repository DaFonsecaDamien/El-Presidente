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
    private Map<NameFaction,Integer> supportersNumber;
    private Map<NameFaction,Integer> satisfactionPercentage;

    public Scenario(List<Event> events, int treasury, int foodUnit, int industryPercentage, int agriculturePercentage, Map<NameFaction, Integer> supportersNumber, Map<NameFaction, Integer> satisfactionPercentage) {
        this.events = events;
        this.treasury = treasury;
        this.foodUnit = foodUnit;
        this.industryPercentage = industryPercentage;
        this.agriculturePercentage = agriculturePercentage;
        this.supportersNumber = supportersNumber;
        this.satisfactionPercentage = satisfactionPercentage;
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getTreasury() {
        return treasury;
    }

    public int getIndustryPercentage() {
        return industryPercentage;
    }

    public int getAgriculturePercentage() {
        return agriculturePercentage;
    }

    public Map<NameFaction, Integer> getSupportersNumber() {
        return supportersNumber;
    }

    public Map<NameFaction, Integer> getSatisfactionPercentage() {
        return satisfactionPercentage;
    }

    public int getFoodUnit() {
        return foodUnit;
    }
}
