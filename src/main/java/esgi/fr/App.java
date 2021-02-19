package esgi.fr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);

        String choiceMode;
        String choiceLevel;

        Mode mode;
        Level level;

        System.out.println("****ELPRESIDENTE****");

        do {
            System.out.println("Choisissez un mode");
            System.out.println("1 - Mode normal");
            System.out.println("2 - Mode bac a sable");
            choiceMode = sc.nextLine();
        }while(!choiceMode.equals("1") && !choiceMode.equals("2"));


        switch (choiceMode){
            case "1":
                mode = Mode.NORMAL;
                System.out.println("Vous avez choisi le mode normal");
                break;
            case "2":
                mode = Mode.SANDBOX;
                System.out.println("Vous avez choisi le mode bac à sable");
                break;
            default:
                mode = Mode.NORMAL;
                break;
        }
        do{
            System.out.println("Choisissez votre niveau de difficulté:");
            System.out.println("1 - Facil");
            System.out.println("2 - Moyen");
            System.out.println("3 - Difficile");
            choiceLevel = sc.nextLine();
        }while(!choiceLevel.equals("1") && !choiceLevel.equals("2") &&!choiceLevel.equals("3"));

        switch (choiceLevel){
            case "1":
               level = Level.Simple;
               System.out.println("Vous avez choisi le niveau Facil");
               break;
            case "2":
                level = Level.Medium;
                System.out.println("Vous avez choisi le niveau Moyen");
                break;
            case "3":
                level = Level.Difficult;
                System.out.println("Vous avez choisi le niveau Difficil");
                break;

            default:
                level = Level.Medium;
                System.out.println("Vous avez choisi le niveau Moyen");
                break;
        }


        //TODO creer une fonction ou une classe pour récuperer le json des infos du jeux, des events, choix, effets, events des choix

        //utilitaire (ces données servent a simuler notre application)
        List<Effect> effects = new ArrayList<>();
        effects.add(new Effect("typeAction","action",3));
        effects.add(new Effect("typeAction","action",3));
        effects.add(new Effect("typeAction","action",3));

            List<Choice> otherChoice = new ArrayList<>();
            otherChoice.add(new Choice(effects,"nameChoice",null));
            otherChoice.add(new Choice(effects,"nameChoice",null));
            otherChoice.add(new Choice(effects,"nameChoice",null));

            List<Event> relatedEvents = new ArrayList<>();
            relatedEvents.add(new Event(otherChoice,"name",Season.WINTER));
            relatedEvents.add(new Event(otherChoice,"name",Season.WINTER));
            relatedEvents.add(new Event(otherChoice,"name",Season.WINTER));

        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice(effects,"nameChoice",relatedEvents));
        choices.add(new Choice(effects,"nameChoice",relatedEvents));
        choices.add(new Choice(effects,"nameChoice",null));

        List<Event> events = new ArrayList<>();
        events.add(new Event(choices,"name",Season.WINTER));
        events.add(new Event(choices,"name",Season.WINTER));
        events.add(new Event(choices,"name",Season.WINTER));

        List<Faction> factions = new ArrayList<>();
        factions.add(new Faction(NameFaction.CAPITALISTE,60,10));
        factions.add(new Faction(NameFaction.COMMUNISTE,60,10));
        factions.add(new Faction(NameFaction.LIBERAU,60,10));
        factions.add(new Faction(NameFaction.RELIGIEU,60,10));
        factions.add(new Faction(NameFaction.MILITARISTE,50,10));
        factions.add(new Faction(NameFaction.ECOLOGISTE,60,10));
        factions.add(new Faction(NameFaction.NATIONALISTE,60,10));
        factions.add(new Faction(NameFaction.LOYALISTE,100,10));

        Scenario scenario = new Scenario(events,30,120,21,54,factions);
        Game game = new Game(level,mode,scenario);
        boolean resultGame = game.run(scenario.getEvents());

        printResult(resultGame);
    }

    private static void printResult(boolean resultGame){
//        afficher le résultat
    }
}
