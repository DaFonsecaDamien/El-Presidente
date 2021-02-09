package esgi.fr;

import java.io.Console;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner sc= new Scanner(System.in);

        String choiceMode;
        String choiceLevel;

        Mode mode;
        Level level;

        System.out.println("****ELPRESIDENTE****");


        System.out.println("Choisissez un mode");
        System.out.println("1 - Mode normal");
        System.out.println("2 - mode bac a sable");
        choiceMode = sc.nextLine();

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

        System.out.println("Choisissez votre niveau de difficulté:");
        System.out.println("1 - Facil");
        System.out.println("2 - Moyen");
        System.out.println("3 - Difficile");
        choiceLevel = sc.nextLine();

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

    }
}
