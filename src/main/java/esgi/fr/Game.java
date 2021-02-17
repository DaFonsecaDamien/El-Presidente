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
            choice = chooseChoice(event);
            //si le choix provoque des evenements ...
            if(event.getChoices().get(choice).getRelatedEvents() != null){
                //...on le parcour! tout en véérifiant si la fonction return pas false pour eviter de boucler pour rien
                if(!run(event.getChoices().get(choice).getRelatedEvents())){
                    return false;
                }
            }
            bribeFactionMenu();
            nextEvent();
        }
        return true;
    }

    // TODO Condition de perte
    private boolean isLoose(){

        return false;
    }

    private int chooseChoice(Event event) {
        int myChoice = 0;
        int i=0;

        System.out.println(event.getName());

        for(Choice choice : event.getChoices()){
            i++;
            System.out.println(i+" : "+choice.getName());
        }


        Scanner sc = new Scanner(System.in);
        while (myChoice < 1 || myChoice > event.getChoices().size()) {
            System.out.println("Rentrez un bon numéro ");
            while (!sc.hasNextInt()) {
                sc = new Scanner(System.in);
                System.out.println("Rentrez un bon numéro ");
            }
            myChoice = sc.nextInt();
        }
        return myChoice-1;
    }


    private void nextEvent(){
        System.out.println("annee numero : "+(year+1));
        System.out.println("saison "+season);
        season = season.next();
        if(season == Season.WINTER){
            year++;
        }
    }

    private void bribeFactionMenu(){

        String choice="";
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Voulez vous soudoyer un faction ?");
            System.out.println("tapez sur 'o' si vous le voulez");
            System.out.println("sinon tapez autre part");
            choice = sc.nextLine();

            if(choice.equals("o")){
                chooseFactions();
            }

        }while(choice.equals("o"));

    }

    private void chooseFactions(){
        String choice="";
        Scanner sc = new Scanner(System.in);
        System.out.println("Voici la liste des factions : ");
        System.out.println("1 - CAPITALISTE");
        System.out.println("2 - COMMUNISTE");
        System.out.println("3 - LIBERAU");
        System.out.println("4 - RELIGIEU");
        System.out.println("5 - MILITARISTE");
        System.out.println("6 - ECOLOGISTE");
        System.out.println("7 - NATIONALISTE");
        System.out.println("8 - LOYALIST");
    }
}
