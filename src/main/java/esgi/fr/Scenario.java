package esgi.fr;

import esgi.fr.Event.Event;
import esgi.fr.Faction.ListFaction;
import esgi.fr.GameParameters.Season;

import java.io.Serializable;
import java.util.List;

public class Scenario implements Serializable {
    private final List<Event> events;
    private final String name;
    private final String story;
    private int treasury;
    private int foodUnit;
    private int industryPercentage;
    private int agriculturePercentage;
    private final ListFaction listFactions;
    private int year;
    private Season season;

    public Scenario(List<Event> events, String name, String story, int agriculturePercentage, int industryPercentage, int treasury, int foodUnit, ListFaction factions) {
        this.events = events;
        this.name = name;
        this.story = story;
        this.treasury = treasury;
        this.foodUnit = foodUnit;
        this.industryPercentage = industryPercentage;
        this.agriculturePercentage = agriculturePercentage;
        this.listFactions = factions;
        this.year = 1;
        this.season = Season.WINTER;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public List<Event> getEvents() {
        return events;
    }

    public String getName() {
        return name;
    }

    public String getStory() {
        return story;
    }

    public int getTreasury() {
        return treasury;
    }

    public void setTreasury(int treasury) {
        if (treasury < 0) {
            this.treasury = 0;
            return;
        }
        this.treasury = treasury;
    }

    public int getIndustryPercentage() {
        return industryPercentage;
    }

    public void setIndustryPercentage(int industryPercentage) {
        if (industryPercentage < 0) {
            this.industryPercentage = 0;
            return;
        }
        this.industryPercentage = industryPercentage;
    }

    public int getAgriculturePercentage() {
        return agriculturePercentage;
    }

    public void setAgriculturePercentage(int agriculturePercentage) {
        if (agriculturePercentage < 0) {
            this.agriculturePercentage = 0;
            return;
        }
        this.agriculturePercentage = agriculturePercentage;
    }

    public int getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(int foodUnit) {
        this.foodUnit = foodUnit;
    }

    public ListFaction getListFactions() {
        return listFactions;
    }

    @Override
    public String toString() {
        return name + "\n\n" +
                story + "\n\n" +
                "Or dans votre trésorerie : " + treasury + "\n" +
                "Unité de nourritures en stock : " + foodUnit + "\n" +
                "Surface de l'ile dédié à l'industrie : " + industryPercentage + "%\n" +
                "Surface de l'ile dédié à l'agriculture : " + agriculturePercentage + "%\n" +
                "Factions : " + listFactions.getFactions() + "\n" +
                "Nous somme en Hiver\n";
    }
}
