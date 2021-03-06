package esgi.fr;

import esgi.fr.Event.Event;
import esgi.fr.GameParameters.Difficulty;
import esgi.fr.GameParameters.Mode;
import esgi.fr.managers.ChoiceManager;
import esgi.fr.managers.FactionManager;

import java.io.*;
import java.util.List;

public class Game implements Serializable {

    private final Difficulty difficulty;
    private final Mode mode;
    private final Scenario scenario;

    public Game(Difficulty difficulty, Mode mode, Scenario scenario) {
        this.difficulty = difficulty;
        this.mode = mode;
        this.scenario = scenario;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Mode getMode() {
        return mode;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public boolean run(List<Event> events) {
        int choice = 0;
        for (Event event : events) {

            System.out.println("Satisfaction global de l'ile : " + FactionManager.getGlobalSatisfactionPercentage(scenario.getListFactions()) + "\n\n");
            if (!ChoiceManager.manageChoice(this,event,difficulty)) {
                return false;
            }
            scenario.setSeason(scenario.getSeason().next());
            scenario.manageAgriculture();
            scenario.manageYear();

            if (event.getChoices().get(choice).getRelatedEvents() != null) {

                if (!run(event.getChoices().get(choice).getRelatedEvents())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isLoose() {
        double globalSatisfaction = FactionManager.getGlobalSatisfactionPercentage(scenario.getListFactions());
        return difficulty.getMinGlobalSatisfaction() > globalSatisfaction;
    }

}
