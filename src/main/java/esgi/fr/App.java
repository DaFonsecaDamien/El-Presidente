package esgi.fr;

import esgi.fr.GameParameters.Difficulty;
import esgi.fr.GameParameters.Mode;
import esgi.fr.datas.GameUtilities;

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

        //if it has files save
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
                    int index = loadIndexEvent();
                    game = loadGame(file);
                    resultGame = game.run(game.getScenario().getEvents(),index);

            }
        }
        else{
            game = newGame();
            resultGame = game.run(game.getScenario().getEvents(),0);
        }

        System.out.println(game.getScenario());
        System.out.println("\n      ******Vous pouvez quitter la partie en tapant sur 'Q'******      \n\n");
        
        printResult(resultGame);

    }


    /**
     *
     * Display the result of the game.
     *
     */
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

    /**
     *
     * Allow the user to save his game.
     * @param index The event ID where the player stopped the game.
     */
    private static void saveGame(int index) {
        File file = new File("save.bin");
        File file2 = new File("save_id.bin");
        if(file.isFile()){
            file.delete();
        }
        if(file2.isFile()){
            file2.delete();
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            objectOutputStream.writeObject(game);

            Writer writer = new BufferedWriter(new FileWriter(file2));
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

    /**
     *
     * Confirmation if the user wants to the leave the game
     *
     */
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


    /**
     *
     * Start a new game.
     *
     */
    private static Game newGame() throws Exception {
        Mode mode;
        Difficulty difficulty;
        String filePath;

        System.out.println("****ELPRESIDENTE****");

        mode = getChoiceMode();
        difficulty = getChoiceDifficulty();

        if(mode==Mode.NORMAL){
            filePath = "src/ressources/scenarios";

        }else{
            filePath = "src/ressources/sandbox";
        }
        Scenario scenario = getStoryFile(filePath);

        return new Game(difficulty, mode, scenario);

    }

    /**
     *
     * Loads the game from the backup file
     *
     */
    private static Game loadGame(File file) {
        Game game = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            Game data = (Game) (objectInputStream.readObject());
            game = new Game(data.getDifficulty(), data.getMode(), data.getScenario());

            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture du fichier de sauvegarde :\n" + e.getMessage());
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            System.out.println("Fichier corrompu !\n" + e.getMessage());
            System.exit(-1);
        }
        return game;
    }


    /**
     *
     * Check the id of the event from the backup file.
     *
     */
    private static int loadIndexEvent() throws IOException{
        int index=0;
        try {
            Reader reader = new BufferedReader(new FileReader("save_id.bin"));
            index = reader.read();
        }
        catch (IOException e){
            System.out.println("Erreur lors de l'ouverture du fichier de sauvegarde :\n" + e.getMessage());
            System.exit(-1);
        }
        return index;
    }


    /**
     *
     * Ask the user to choose a mode.
     *
     */
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


    /**
     *
     * Ask the user to choose a difficulty.
     *
     */
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


    /**
     *
     * Get the story file.
     *
     */
    private static Scenario getStoryFile(String filePath) throws FileNotFoundException {
        String scenarioSelected = "";
        Scanner sc = new Scanner(System.in);

        List<File> file = GameUtilities.allJsonFromDir(new File(filePath));
        Map<Integer, Map<String, String>> sandBoxFilesNames = GameUtilities.getScenarioName(file);

        for (Map.Entry<Integer, Map<String, String>> indexEntry : sandBoxFilesNames.entrySet()) {
            for (Map.Entry<String, String> entry : indexEntry.getValue().entrySet()) {
                System.out.println(indexEntry.getKey() + " - " + entry.getValue());
            }
        }
        int choiceConfigurationFile = 0;
        do {
            System.out.println("Choisissez un fichier : ");
            choiceConfigurationFile = sc.nextInt();
        } while (choiceConfigurationFile < 1 || choiceConfigurationFile > sandBoxFilesNames.size());

        Map<String, String> pathName = sandBoxFilesNames.get(choiceConfigurationFile);

        scenarioSelected = "";
        for (Map.Entry<String, String> pathMap : pathName.entrySet()) {
            scenarioSelected = pathMap.getKey();
        }
        Scenario scenario = GameUtilities.parseJsonToObject(scenarioSelected);

        return scenario;

    }
}
