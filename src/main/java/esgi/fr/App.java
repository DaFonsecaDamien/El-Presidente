package esgi.fr;

import esgi.fr.GameParameters.Difficulty;
import esgi.fr.GameParameters.Mode;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    static Game game;
    public static void main(String[] args) throws Exception {
        File file = new File("save.bin");
        File file2 = new File("save_id.bin");
        boolean resultGame = false;
        if (file.isFile() && file2.isFile()) {
            Scanner sc = new Scanner(System.in);
            String choiceLoad = "";

            do {
                System.out.println("Il y'a une sauvegarde voulez vous la charger ou en ccommencer une nouvelle ?");
                System.out.println("1 - Commencer une nouvelle partie");
                System.out.println("2 - Charger la partie existante\n\n");
                choiceLoad = sc.nextLine();
            } while (!choiceLoad.equals("1") && !choiceLoad.equals("2"));
            switch (choiceLoad) {
                case "1":
                    game = newGame();
                    resultGame = game.run(game.getScenario().getEvents(),0);
                    break;
                default:
                    resultGame = loadGame(file,file2);
            }
        } else{
            game = newGame();
            resultGame = game.run(game.getScenario().getEvents(),0);
        }

        System.out.println(game.getScenario());
        System.out.println("\n      ******Vous pouvez quitter la partie en tapant sur 'Q'******      \n\n");
        
        printResult(resultGame);

    }

    private static Game newGame() throws Exception {
        Mode mode;
        Difficulty difficulty;
        Scanner sc = new Scanner(System.in);

        System.out.println("****ELPRESIDENTE****");

        mode = getChoiceMode();
        difficulty = getChoiceDifficulty();
        String scenarioSelected = "";

        if(mode==Mode.NORMAL){

            String scenarioDir = "src/ressources/scenarios";


            List<File> scenariosFiles = GameUtilities.allJsonFromDir(new File(scenarioDir));
            Map<Integer, Map<String, String>> scenarioFilesNames = GameUtilities.getScenarioName(scenariosFiles);

            for (Map.Entry<Integer, Map<String, String>> indexEntry : scenarioFilesNames.entrySet()) {
                for (Map.Entry<String, String> entry : indexEntry.getValue().entrySet()) {
                    System.out.println(indexEntry.getKey() + " - " + entry.getValue());
                }
            }
            int choiceScenario = 0;
            do {
                System.out.println("Veuillez choisir un scenario : ");
                choiceScenario = sc.nextInt();
            } while (choiceScenario < 1 || choiceScenario > scenarioFilesNames.size());

            Map<String, String> pathName = scenarioFilesNames.get(choiceScenario);

            scenarioSelected = "";
            for (Map.Entry<String, String> pathMap : pathName.entrySet()) {
                scenarioSelected = pathMap.getKey();
            }
        }
        else{

            String sandBoxDir = "src/ressources/sandbox";

            List<File> sandBoxFile = GameUtilities.allJsonFromDir(new File(sandBoxDir));
            Map<Integer, Map<String, String>> sandBoxFilesNames = GameUtilities.getScenarioName(sandBoxFile);

            for (Map.Entry<Integer, Map<String, String>> indexEntry : sandBoxFilesNames.entrySet()) {
                for (Map.Entry<String, String> entry : indexEntry.getValue().entrySet()) {
                    System.out.println(indexEntry.getKey() + " - " + entry.getValue());
                }
            }
            int choiceConfigurationFile = 0;
            do {
                System.out.println("Veuillez choisir un fichier de configuration : ");
                choiceConfigurationFile = sc.nextInt();
            } while (choiceConfigurationFile < 1 || choiceConfigurationFile > sandBoxFilesNames.size());

            Map<String, String> pathName = sandBoxFilesNames.get(choiceConfigurationFile);

            scenarioSelected = "";
            for (Map.Entry<String, String> pathMap : pathName.entrySet()) {
                scenarioSelected = pathMap.getKey();
            }

        }



        Scenario scenario = GameUtilities.parseJsonToObject(scenarioSelected);
        return new Game(difficulty, mode, scenario);

    }

    private static boolean loadGame(File file,File file2) {
        Game game = null;
        int index=0;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            Game datas = (Game) (objectInputStream.readObject());
            game = new Game(datas.getDifficulty(), datas.getMode(), datas.getScenario());

            Reader reader = new BufferedReader(new FileReader(file2));
            index = reader.read();

            objectInputStream.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture du fichier de sauvegarde :\n" + e.getMessage());
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            System.out.println("Fichier corrompu !\n" + e.getMessage());
            System.exit(-1);
        }
        return game.run(game.getScenario().getEvents(),index);
    }

    private static void printResult(boolean resultGame) {
        if (resultGame) {
            System.out.println("Felicitation vous avez fait les bons choix, vous  êtes le meilleur président que le monde n'ai jammais connu");
            System.out.println("Vous avez su prendre les bonnes décisions aux bon moments");
            System.out.println("Nous espérons voir votre patrie s'agrandir et se développer d'avantages");
            System.out.println("A la prochaine !");
        } else {
            System.out.println("Coup d'etat !!");
            System.out.println("Votre patrie vous a rejeté !");
            System.out.println("Ca n'est pas si facile de devenir le président parfait");
            System.out.println("Retentez votre chance une prochaine fois");
        }
    }

    private static Mode getChoiceMode(){
        Mode mode = Mode.NORMAL;
        Scanner sc = new Scanner(System.in);
        String choiceMode;
        do {
            System.out.println("Choisissez un mode");
            System.out.println("1 - Mode normal");
            System.out.println("2 - Mode bac a sable");
            choiceMode = sc.nextLine();
        } while (!choiceMode.equals("1") && !choiceMode.equals("2"));


        switch (choiceMode) {
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
        return mode;
    }

    private static Difficulty getChoiceDifficulty(){
        Scanner sc = new Scanner(System.in);
        String choiceDifficulty;
        Difficulty difficulty = Difficulty.NORMAL;
        do {
            System.out.println("Choisissez votre niveau de difficulté:");
            System.out.println("1 - Facile");
            System.out.println("2 - Moyen");
            System.out.println("3 - Difficile");
            choiceDifficulty = sc.nextLine();
        } while (!choiceDifficulty.equals("1") && !choiceDifficulty.equals("2") && !choiceDifficulty.equals("3"));

        switch (choiceDifficulty) {
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
        return difficulty;
    }

    private static void saveGame(int index) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("save.bin")));
            objectOutputStream.writeObject(game);

            Writer writer = new BufferedWriter(new FileWriter("save_id.bin"));
            writer.write(index);

            System.out.println("Votre partie a été sauvegardé avec succée !");
            objectOutputStream.close();
            writer.close();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Impossible de savegarder votre partie : ");
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public static void menuQuitGame(int index) {
        Scanner sc = new Scanner(System.in);
        String choice = "";
        do {
            System.out.println("Voulez vous vraiment quitter ?");
            System.out.println("1 - Oui");
            System.out.println("2 - Non");
            choice = sc.nextLine();

            if (choice.equals("1")) {
                saveGame(index);
            }

        } while (!choice.equals("1") && !choice.equals("2"));
    }
}
