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
        this.year = 1;
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
            season = season.next();
            manageAgriculture();
            manageYear();
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

    double globalSatisfaction = scenario.getListFactions().getAllSatisfactionNumber();

        return globalSatisfaction < 10.0;
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
        if(season == Season.WINTER){
            System.out.println("Nous voici en fin d'année !");

            manageTreasurer();

            System.out.println("Vous avez au total : "+scenario.getTreasury()+" pieces d'or dans votre trésorerie\n");
            System.out.println("Vous avez au total "+scenario.getFoodUnit()+" unités de nouriture en stock");

            bribeFactionMenu();
            marketPlace();
            year++;
        }
        System.out.println("annee numero : "+year);
        System.out.println("saison "+season);
    }

    private void bribeFactionMenu(){

        String choice="";
        Scanner sc = new Scanner(System.in);
        do{
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
        }
        printResultBribe(nameFactionChoose);
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
            System.out.println(faction);
            System.out.println("Or requis : "+faction.getSupportersNumber()*15);
            System.out.println();
        }
        System.out.println("choisissez celle que vous voulez soudoyer");
    }

    private void printResultBribe(NameFaction nameFactionChoose){
        System.out.println("Vous avez choisi de soudoyer la factions des "+nameFactionChoose+"S");

        Faction factionChosen = scenario.getListFactions().getOneFaction(nameFactionChoose);

        if(factionChosen.bribeFaction(scenario.getTreasury())){
            scenario.setTreasury(scenario.getTreasury()-15*factionChosen.getSupportersNumber());
            System.out.println("Leur satisfaction a auguementé de 10%!\n");

            Faction loyalistFaction = scenario.getListFactions().getOneFaction(NameFaction.LOYALISTE);
            loyalistFaction.setSatisfactionPercentage(loyalistFaction.getSatisfactionPercentage()-(factionChosen.getSupportersNumber()*15)/10);
            System.out.println("La satisfaction de la faction loyaliste est desormais de "+loyalistFaction.getSatisfactionPercentage()+"%\n");
        }else{
            System.out.println("Vous n'etes pas en meusure de soudoyer cette faction\n");
        }
    }

    private void manageAgriculture(){
        int foodUnitConsumed = scenario.getListFactions().getAllSuportersNumber();
        scenario.setFoodUnit(scenario.getFoodUnit()-foodUnitConsumed);
        System.out.println("Votre ile a consomé "+foodUnitConsumed+" unités de nourriture");

        int gainFood = 10*scenario.getAgriculturePercentage();
        scenario.setFoodUnit(scenario.getFoodUnit()+gainFood);
        System.out.println("Voici ce que l'ile vous a rapporté en nouriture cette saison: "+gainFood);
    }

    private void manageTreasurer(){
        int gainOr = 10*scenario.getIndustryPercentage();
        scenario.setTreasury(scenario.getTreasury()+gainOr);
        System.out.println("Voic ce que l'ile vous rapporte en or cette année: "+gainOr);
    }

    private void marketPlace(){
        String choice = "";
        Scanner sc = new Scanner(System.in);

        System.out.println("Vous pouvez achetez des unites de nouritures pour subvenir aux besoins");
        System.out.println("des citoyens de votre ile\n");
        System.out.println("Vous avez "+scenario.getListFactions().getAllSuportersNumber()+" citoyens dans votre ile");
        System.out.println("Vous avez "+scenario.getFoodUnit()+" unités de nourriture\n");
        System.out.println("Soyez prévoyant !\n");

        System.out.println("Voulez vous achetez des unités de nourriture ?");
        System.out.println("Cette opération vous coutera 8 or par unité\n");
        System.out.println("Si oui tapez sur 'o' sinon tapez sur n'importe quelle autres touches\n");

        choice = sc.nextLine();
        if(choice.equals("o")){
            buyFoodUnits();
        }
    }

    private void buyFoodUnits(){
        int nbFoodUnitBought=0;
        Scanner sc = new Scanner(System.in);
        int nbUnitFoodMaxPossible = scenario.getTreasury() / 8;
        System.out.println("Combien d'unités de nourriture voulez vous acheter ?");

        while(nbFoodUnitBought<1 || nbFoodUnitBought > nbUnitFoodMaxPossible){
            System.out.println("Vous pouvez achetez jusqu'a "+nbUnitFoodMaxPossible+" unités de nourriture\n");
            while (!sc.hasNextInt()){
                sc = new Scanner(System.in);
            }
            nbFoodUnitBought = sc.nextInt();
        }
        scenario.setTreasury(scenario.getTreasury()-8*nbFoodUnitBought);
        scenario.setFoodUnit(scenario.getFoodUnit()+nbFoodUnitBought);

        System.out.println("Vous avez acheté "+nbFoodUnitBought+" unités de nourriture !");
        System.out.println("Vous avez dépensé au total "+nbFoodUnitBought*8+" pièces d'or\n");

    }



}
