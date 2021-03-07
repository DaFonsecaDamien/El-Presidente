package esgi.fr.managers;

import esgi.fr.App;
import esgi.fr.utilities.Event.Choice;
import esgi.fr.utilities.Event.Event;
import esgi.fr.Game;
import esgi.fr.GameParameters.Difficulty;

import java.util.Scanner;

public class ChoiceManager {

    public static boolean manageChoice(Game game, Event event, Difficulty difficulty,int index) {
        int choice = 0;
        int i = 0;

        System.out.println(event.getName());

        for (Choice theChoice : event.getChoices()) {
            i++;
            System.out.println(i + " : " + theChoice.getName());
        }

        Scanner sc = new Scanner(System.in);
        while (choice < 1 || choice > event.getChoices().size()) {
            while (!sc.hasNextInt()) {
                if (sc.next().equals("Q")) {
                    App.menuQuitGame(index);
                }
                sc = new Scanner(System.in);
            }
            choice = sc.nextInt();
        }
        EffectManager.manageEffect(game.getScenario(),choice - 1, event,difficulty);

        return !game.isLoose();
    }

}
