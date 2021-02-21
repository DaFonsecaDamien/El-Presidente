package esgi.fr;

import java.io.File;
import java.util.*;

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
            System.out.println("1 - Facile");
            System.out.println("2 - Moyen");
            System.out.println("3 - Difficile");
            choiceLevel = sc.nextLine();
        }while(!choiceLevel.equals("1") && !choiceLevel.equals("2") &&!choiceLevel.equals("3"));

        switch (choiceLevel){
            case "1":
               level = Level.Simple;
               System.out.println("Vous avez choisi le niveau Facile");
               break;
            case "2":
                level = Level.Medium;
                System.out.println("Vous avez choisi le niveau Moyen");
                break;
            case "3":
                level = Level.Difficult;
                System.out.println("Vous avez choisi le niveau Difficile");
                break;

            default:
                level = Level.Medium;
                System.out.println("Vous avez choisi le niveau Moyen");
                break;
        }


//        //TODO creer une fonction ou une classe pour récuperer le json des infos du jeux, des events, choix, effets, events des choix
//
        //utilitaire (ces données servent a simuler notre application)
        List<Effect> effects = new ArrayList<>();
            Map<String ,Integer> actionsOnFaction = new HashMap<>();
            actionsOnFaction.put("ECOLOGISTS",12);
            actionsOnFaction.put("RELIGIOUS",-2);

            Map<String ,Integer> actionSuporters = new HashMap<>();
            actionSuporters.put("partisans",-12);

            Map<String ,Integer> actionOnFactor = new HashMap<>();
            actionOnFactor.put("INDUSTRY",12);
            actionOnFactor.put("TREASURY",-2);
            actionOnFactor.put("AGRICULTURE",-4);

        effects.add(new Effect("actionOnFaction",actionsOnFaction));
        effects.add(new Effect(null,actionSuporters));
        effects.add(new Effect("actionOnFactor",actionOnFactor));

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

        String name ="Attaque des titans";
        String story = "une bien longue histoir";

        List<Faction> factions = new ArrayList<>();
        factions.add(new Faction(NameFaction.CAPITALISTE,60,10));
        factions.add(new Faction(NameFaction.COMMUNISTE,60,10));
        factions.add(new Faction(NameFaction.LIBERAU,60,10));
        factions.add(new Faction(NameFaction.RELIGIEU,60,10));
        factions.add(new Faction(NameFaction.MILITARISTE,50,10));
        factions.add(new Faction(NameFaction.ECOLOGISTE,60,10));
        factions.add(new Faction(NameFaction.NATIONALISTE,60,10));
        factions.add(new Faction(NameFaction.LOYALISTE,100,10));
        ListFaction listFaction = new ListFaction(factions);

        Scenario scenario = new Scenario(events,name,story,700,500,35,40,listFaction);
        Game game = new Game(level,mode,scenario);
        boolean resultGame = game.run(scenario.getEvents());

        printResult(resultGame);

        //String scenarioDir = ".\\src\\ressources\\scenarios";
        //String scenarioTest = ".\\src\\ressources\\scenarios\\attackOnTitans.json";
        //List<File> scenariosJson = GameUtilities.allJsonFromDir(new File(scenarioDir));
        //System.out.println(scenariosJson);
        //GameUtilities.parseJsonToObject(scenarioTest);
    }

    private static void printResult(boolean resultGame){
        if(resultGame){
            System.out.println("Felicitation vous avez fait les bons choix, vous  etes le meilleur presendent que le monde ai jammais connu");
            System.out.println("Vous avez su prendre les bonnes décisions aux bon moments");
            System.out.println("Nous espérons voir votre patrie s'agrandir et se développer d'avantages");
            System.out.println("A la prochaine !");
        }else{
            System.out.println("Coup d'etat !!");
            System.out.println("Votre patrie vous a rejeté !");
            System.out.println("Ca n'est pas si facil de devenir le présidents parfait");
            System.out.println("Retentez votre chance une prochaine fois");
        }
    }
}
