package esgi.fr;

import java.util.List;
import java.util.Scanner;

public class Game {

    private Level level;
    private Mode mode;
    private Scenario scenario;
    private int year;
    private Season season;

    public Game(Level level, Mode mode, Scenario scenario) {
        this.level = level;
        this.mode = mode;
        this.scenario = scenario;
        this.year = 0;
        this.season = Season.WINTER;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public boolean run(List<Event> events){
        int choice=0;
        //on parcours la list de event
        for(Event event: events){
            //on vérifie si on a pas perdu lol
            if(isLoose()){
                //afficher les resultats et sortir
                return false;
            }
            //le user choisit un choix
            choice = chooseChoice(event.getChoices().size());
            //si le choix provoque des evenements ...
            if(event.getChoices().get(choice).getRelatedEvents() != null){
                //...on le parcour! tout en véérifiant si la fonction return pas false pour eviter de boucler pour rien
                if(!run(event.getChoices().get(choice).getRelatedEvents())){
                    return false;
                }
            }
            System.out.println("annee numero : "+(year+1));
            System.out.println("saison "+season);
            this.season = season.next();
            if(this.season == Season.WINTER){
                this.year++;
            }
        }
        return true;
    }

    // TODO Condition de perte
    private boolean isLoose(){
   return Faction.globalSatisfaction > 30.0;
    }




    private static int chooseChoice(int numberOfChoicePossible){
        String choice;
        Scanner sc= new Scanner(System.in);
        //do{
            System.out.println("Que choisissez vous ?");
            choice = sc.nextLine();
            return Integer.parseInt(choice);

        //}while();
    }
}
