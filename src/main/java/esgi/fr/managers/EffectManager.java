package esgi.fr.managers;

import esgi.fr.Event.Effect;
import esgi.fr.Event.Event;
import esgi.fr.Game;
import esgi.fr.GameParameters.Difficulty;
import esgi.fr.Scenario;

import java.awt.*;

public class EffectManager {

    public static void manageEffect(Scenario scenario, int choice, Event event, Difficulty difficulty) {
        System.out.println("Conséquences : ");
        System.out.println("\n-------------------------------\n");
        for (Effect effect : event.getChoices().get(choice).getEffects()) {

            if (effect.getTypeAction() == null) {
                effect.manageEffectSupporters(scenario.getListFactions(),difficulty);
            } else if (effect.getTypeAction().equals("actionOnFaction")) {
                effect.manageEffectOnFaction(difficulty,scenario.getListFactions());
            } else if (effect.getTypeAction().equals("actionOnFactor")) {
                effect.manageEffectOnFactor(scenario,difficulty);
            }
        }
        System.out.println("\n-------------------------------\n");
    }

}
