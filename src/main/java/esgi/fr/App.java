package esgi.fr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);

        String choiceMode;
        String choiceLevel;

        Mode mode;
        Difficulty difficulty = Difficulty.NORMAL;

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
               difficulty = Difficulty.EASY;
               System.out.println("Vous avez choisi le niveau Facil");
               break;
            case "2":
                difficulty = Difficulty.NORMAL;
                System.out.println("Vous avez choisi le niveau Moyen");
                break;
            case "3":
                difficulty = Difficulty.HARD;
                System.out.println("Vous avez choisi le niveau Difficil");
                break;
        }

        String scenarioDir = "src/ressources/scenarios";

        List<File> scenariosJson = GameUtilities.allJsonFromDir(new File(scenarioDir));
        System.out.println(scenariosJson);
        Map<Integer,Map<String,String>> scenarioNames =  GameUtilities.getScenarioName(scenariosJson);

        for (Map.Entry<Integer, Map<String, String>> indexEntry : scenarioNames.entrySet()) {
            for(Map.Entry<String, String> entry : indexEntry.getValue().entrySet()){
                System.out.println(indexEntry.getKey() + " - " +entry.getValue());
            }
        }
        int choiceScenario = 0;
        do{
            System.out.println("Go choisir mon gars : ");
            choiceScenario = sc.nextInt();
        }while(choiceScenario < 1 || choiceScenario > scenarioNames.size());

        Map<String,String> pathName = scenarioNames.get(choiceScenario);

        String scenarioTest = "";
        for (Map.Entry<String, String> pathMap : pathName.entrySet()) {
            scenarioTest = pathMap.getKey();
        }
  
        Scenario scenario = GameUtilities.parseJsonToObject(scenarioTest);
        Game game = new Game(difficulty,mode,scenario);
        System.out.println(scenario);

        boolean resultGame = game.run(scenario.getEvents());

        printResult(resultGame);

    }

    private static void printResult(boolean resultGame){
        if(resultGame){
            System.out.println("Felicitation vous avez fait les bons choix, vous  êtes le meilleur président que le monde n'ai jammais connu");
            System.out.println("Vous avez su prendre les bonnes décisions aux bon moments");
            System.out.println("Nous espérons voir votre patrie s'agrandir et se développer d'avantages");
            System.out.println("A la prochaine !");
        }else{
            System.out.println("Coup d'etat !!");
            System.out.println("Votre patrie vous a rejeté !");
            System.out.println("Ca n'est pas si facile de devenir le président parfait");
            System.out.println("Retentez votre chance une prochaine fois");
        }
    }
}
