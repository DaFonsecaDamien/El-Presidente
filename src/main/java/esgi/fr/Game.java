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
        this.season = Season.SPRING;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public boolean run(List<Event> events){
        int choice=0;
        //on parcours la list de event
        for(Event event: events){
            manageYear();
            //on vérifie si on a pas perdu lol
            if(isLoose()){
                //afficher les resultats et sortir
                return false;
            }
            //le user choisit un choix
            choice = chooseChoice(event);
            season = season.next();
            //si le choix provoque des evenements ...
            if(event.getChoices().get(choice).getRelatedEvents() != null){
                //...on le parcour! tout en véérifiant si la fonction return pas false pour eviter de boucler pour rien
                if(!run(event.getChoices().get(choice).getRelatedEvents())){
                    return false;
                }
            }
        }
        return true;
    }

    // TODO Condition de perte
    private boolean isLoose(){

        return Faction.globalSatisfaction > 30.0;
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
            while (!sc.hasNextInt()) {
                sc = new Scanner(System.in);
                System.out.println("Rentrez un bon numéro ");
            }
            myChoice = sc.nextInt();
        }
        return myChoice-1;
    }


    private void manageYear(){
        System.out.println("annee numero : "+(year+1));
        System.out.println("saison "+season);
        if(season == Season.WINTER){
            System.out.println("Nous voici en fin d'année !");
            bribeFactionMenu();
            year++;
        }
    }

    private void bribeFactionMenu(){

        String choice="";
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Vous avez "+scenario.getTreasury()+" pieces d'or dans votre trésorerie\n");
            System.out.println("Voulez vous soudoyer une faction ?");
            System.out.println("Si oui tapez sur 'o' sinon tapez sur n'importe quelle autres touches");
            choice = sc.nextLine();

            if(choice.equals("o")){
                chooseFactionsToBribe();
            }

        }while(choice.equals("o"));

    }

    private void chooseFactionsToBribe(){
        int choice=0;
        NameFaction nameFactionChoose = NameFaction.CAPITALISTE;

        choice = getChoiceFaction();

        switch (choice){
            case 1:
                nameFactionChoose = NameFaction.CAPITALISTE;
                break;
            case 2:
                nameFactionChoose = NameFaction.COMMUNISTE;
                break;
            case 3:
                nameFactionChoose = NameFaction.LIBERAU;
                break;
            case 4:
                nameFactionChoose = NameFaction.RELIGIEU;
                break;
            case 5:
                nameFactionChoose = NameFaction.MILITARISTE;
                break;
            case 6:
                nameFactionChoose = NameFaction.ECOLOGISTE;
                break;
            case 7:
                nameFactionChoose = NameFaction.NATIONALISTE;
                break;
            case 8:
                nameFactionChoose = NameFaction.LOYALISTE;
                break;
        }
        System.out.println("Vous avez choisi de soudoyer la factions des "+nameFactionChoose+"S");

        Faction factionChosen = scenario.getListFactions().getOneFaction(nameFactionChoose);

        if(factionChosen.bribeFaction(scenario.getTreasury())){
            scenario.setTreasury(scenario.getTreasury()-15*factionChosen.getSupportersNumber());

            Faction loyalistFaction = scenario.getListFactions().getOneFaction(NameFaction.LOYALISTE);
            loyalistFaction.setSatisfactionPercentage(loyalistFaction.getSatisfactionPercentage()-(factionChosen.getSupportersNumber()*15)/10);
            System.out.println("La satisfaction de la faction "+nameFactionChoose+" a auguementé de 10%!");
        }else{
            System.out.println("Vous n'etes pas en meusure de soudoyer cette faction");
        }
    }

    private int getChoiceFaction(){
        int choice =0;
        printInfosFactions();
        Scanner sc = new Scanner(System.in);
        while (choice<1 || choice >scenario.getListFactions().getFactions().size()-1) {
            while (!sc.hasNextInt()) {
                sc = new Scanner(System.in);
            }
            choice = sc.nextInt();
        }

        return choice;
    }

    private void printInfosFactions(){
        int i=0;
        System.out.println("Voici la liste des factions : ");
        for(Faction faction : scenario.getListFactions().getFactions()){
            if(faction.getNameFaction()==NameFaction.LOYALISTE)break;
            i++;
            System.out.println(i+" - ");
            System.out.println(faction.toString());
            System.out.println("Or requis : "+faction.getSupportersNumber()*15);
            System.out.println();
        }
        System.out.println("choisissez celle que vous voulez soudoyer");
    }
}
