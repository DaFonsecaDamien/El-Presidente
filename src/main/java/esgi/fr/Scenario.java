package esgi.fr;

import java.util.List;
import java.util.Map;

public class Scenario {
    private List<Event> events;
    private int Treasury;
    private int industryPercentage;
    private int AgriculturePercentage;
    private Map<Faction,Integer> supportersNumber;
    private Map<Faction,Integer> satisfactionPercentage;
    private int foodUnit;

    public List<Event> getEvents() {
        return events;
    }

    public int getTreasury() {
        return Treasury;
    }

    public int getIndustryPercentage() {
        return industryPercentage;
    }

    public int getAgriculturePercentage() {
        return AgriculturePercentage;
    }

    public Map<Faction, Integer> getSupportersNumber() {
        return supportersNumber;
    }

    public Map<Faction, Integer> getSatisfactionPercentage() {
        return satisfactionPercentage;
    }

    public int getFoodUnit() {
        return foodUnit;
    }
}
