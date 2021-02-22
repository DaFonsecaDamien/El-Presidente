package esgi.fr;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {

    private Difficulty difficulty;
    private Mode mode;
    private Scenario scenario;
    private int year;
    private Season season;

    public Game(Difficulty difficulty, Mode mode, Scenario scenario) {
        this.difficulty = difficulty;
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
        for(Event event: events){
            System.out.println("Satisfaction global de l'ile : "+scenario.getListFactions().getGlobalSatisfactionPercentage()+"\n\n");
            if(!chooseChoice(event)){
               return false;
            }
            season = season.next();
            manageAgriculture();
            manageYear();

            if(event.getChoices().get(choice).getRelatedEvents() != null){
                if(!run(event.getChoices().get(choice).getRelatedEvents())){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isLoose(){
        double globalSatisfaction = scenario.getListFactions().getGlobalSatisfactionPercentage();
        return globalSatisfaction < 10.0;
    }

    private boolean chooseChoice(Event event) {
        int choice = 0;
        int i=0;

        System.out.println(event.getName());

        for(Choice theChoice : event.getChoices()){
            i++;
            System.out.println(i+" : "+theChoice.getName());
        }

        Scanner sc = new Scanner(System.in);
        while (choice < 1 || choice > event.getChoices().size()) {
            while (!sc.hasNextInt()) {
                sc = new Scanner(System.in);
                System.out.println("Rentrez un bon numéro ");
            }
            choice = sc.nextInt();
        }
        manageEffect(choice-1,event);

        if(isLoose()){
            return false;
        }
        return true;
    }

    private void manageYear(){
        if(season == Season.WINTER){
            System.out.println("\n\nNous voici en fin d'année !\n\n");

            manageTreasurer();
            printInfosIle();
            bribeFactionMenu();
            marketPlace();
            yearBilan();
            year++;
        }
        System.out.println("Annee numero : "+year);
        System.out.println("Saison "+season);
    }

    private void bribeFactionMenu(){

        int choiceFaction=0;
        String accept="";
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Voulez vous soudoyer une faction ?");
            System.out.println("Si oui tapez sur 'o' sinon tapez sur 'n'");
            accept = sc.nextLine();

            if(accept.equals("o")){
                choiceFaction = getChoiceFaction();
                Faction factionChosen = scenario.getListFactions().chooseFaction(choiceFaction);
                printResultBribe(factionChosen);
            }

        }while(!accept.equals("n"));printInfosFactions();

    }

    private int getChoiceFaction(){
        int choice =0;
        printInfosFactions();
        Scanner sc = new Scanner(System.in);
        while (choice<1 || choice >scenario.getListFactions().getFactions().size()) {
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
            i++;
            System.out.println(i+" - ");
            System.out.println(faction);
            System.out.println("Or requis : "+faction.getSupportersNumber()*15);
            System.out.println();
        }
        System.out.println("choisissez celle que vous voulez soudoyer");
    }

    private void printResultBribe(Faction factionChosen){

        if(factionChosen.getSupportersNumber()*15>scenario.getTreasury()){
            System.out.println("Pas assez d'argent");
            return;
        }

        System.out.println("Vous avez choisi de soudoyer la faction des "+factionChosen.getNameFaction()+"S");
        if(factionChosen.bribeFaction()){
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
        System.out.println("Votre ile a consommé "+foodUnitConsumed+" unités de nourriture");

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
        System.out.println("Si oui tapez sur 'o' sinon tapez sur 'n'\n");

        do{
            choice = sc.nextLine();
        }while (!choice.equals("o") && !choice.equals("n"));

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

    private void yearBilan(){
        System.out.println("C'est l'heure du bilan de fin d'année ! \n");

        if(scenario.getFoodUnit() + 40 * scenario.getAgriculturePercentage() < scenario.getListFactions().getAllSuportersNumber()){
            System.out.println(" Malheureusement vous n'avez pas de quoi nourrir tous vos citoyens ...  \n");
            killPeople();
        }else if(40 * scenario.getAgriculturePercentage() >= scenario.getListFactions().getAllSuportersNumber() * 1.10){
            System.out.println(" Bonne nouvelle : votre agricultre se porte bien  !\n");
            increasePeople();
        }
    }

    private void killPeople(){
        System.out.println(" Vos citoyens meurent de faim ... !\n");
        int supportersBeforeStarving = scenario.getListFactions().getAllSuportersNumber();
        do{
            Faction randomFaction = scenario.getListFactions().getRandomFaction();
            randomFaction.setSupportersNumber(randomFaction.getSupportersNumber() - 1);

            scenario.getListFactions().setAllSatisfaction(-2);

        }while(scenario.getFoodUnit() + 40 * scenario.getAgriculturePercentage() < scenario.getListFactions().getAllSuportersNumber());
        int supportersAfterStarving = supportersBeforeStarving - scenario.getListFactions().getAllSuportersNumber();
        System.out.println(" La famine est terrible vous avez perdu " + supportersAfterStarving + " citoyen(s)  !\n");
    }

    private void increasePeople(){
        System.out.println(" Si bien que la natalité de votre patrie augmente !\n");
        int randomPercentage = (int)(Math.random() * 10) + 1;
        int totalNumbersOfSuportersToAdd = randomPercentage * scenario.getListFactions().getAllSuportersNumber() / 100;

        scenario.getListFactions().addSpportersInFactions(totalNumbersOfSuportersToAdd);
    }

    private void manageEffect(int choice,Event event){
        System.out.println("Conséquences : ");
        System.out.println("\n-------------------------------\n");
        for(Effect effect : event.getChoices().get(choice).getEffects()){

            if(effect.getTypeAction()==null){
                getEffectSupporters(effect);
            }

            else if(effect.getTypeAction().equals("actionOnFaction")){
               getEffectOnFaction(effect);
            }

            else if(effect.getTypeAction().equals("actionOnFactor")){
                getEffectOnFactor(effect);
            }
        }
        System.out.println("\n-------------------------------\n");
    }

    private void getEffectSupporters(Effect effect){
        int value = effect.getActions().get("partisans");

        if(value<0 && scenario.getListFactions().getAllSuportersNumber()>0){
            scenario.getListFactions().removeSpportersInFactions(value);
            System.out.println("-    "+(-value)+" citoyens ont quitté l'ile");
        }
        else if(value>0){
            scenario.getListFactions().addSpportersInFactions(value);
            System.out.println("-    "+value+" citoyens ont rejoint votre ile");
        }
        if(scenario.getListFactions().getAllSuportersNumber()==0){System.out.println("-    Aucun citoyens dans votre ile");}
    }

    private void getEffectOnFaction(Effect effect){
        for (Map.Entry action : effect.getActions().entrySet()){
            int value = action.getValue().hashCode();
            NameFaction nameFaction = NameFaction.CAPITALISTE;
            switch (action.getKey().toString()){
                case "ECOLOGISTS":
                    nameFaction = NameFaction.ECOLOGISTE;
                    break;
                case "CAPITALISTS":
                    nameFaction = NameFaction.CAPITALISTE;
                    break;
                case "LOYALISTS":
                    nameFaction = NameFaction.LOYALISTE;
                    break;
                case "LIBERALS":
                    nameFaction = NameFaction.LIBERAU;
                    break;
                case "RELIGIOUS":
                    nameFaction = NameFaction.RELIGIEU;
                    break;
                case "MILITARISTS":
                    nameFaction = NameFaction.MILITARISTE;
                    break;
                case "NATIONALISTS":
                    nameFaction = NameFaction.NATIONALISTE;
                    break;
                case "COMMUNISTS":
                    nameFaction = NameFaction.COMMUNISTE;
                    break;
            }
            Faction faction = scenario.getListFactions().getOneFaction(nameFaction);
            faction.setSatisfactionPercentage(faction.getSatisfactionPercentage()+value);
            System.out.println("-    "+value+"% de satisfaction chez les "+nameFaction+"S");
        }
    }

    private void getEffectOnFactor(Effect effect){
        for (Map.Entry action : effect.getActions().entrySet()) {
            int value = action.getValue().hashCode();
            switch (action.getKey().toString()) {
                case "AGRICULTURE":
                    if(value<0 && scenario.getAgriculturePercentage()>0){
                        scenario.setAgriculturePercentage(scenario.getAgriculturePercentage()+value);
                    }
                    else if(value>0){
                        manageIndustryAndAgricultureCumul(value,"AGRICULTURE");
                    }
                    System.out.println("-    "+value+"% de surface dédié à l'agriculture");
                    if(scenario.getAgriculturePercentage()==0){System.out.println("-    Aucun pourcentage de surface dédié pour l'agriculture");}
                    break;
                case "INDUSTRY":
                    if(value<0 && scenario.getIndustryPercentage()>0){
                        scenario.setIndustryPercentage(scenario.getIndustryPercentage()+value);
                    }
                    else if(value>0){
                        manageIndustryAndAgricultureCumul(value,"INDUSTRY");
                    }
                    System.out.println("-    "+value+"% de surface dédié à l'industrie");
                    if(scenario.getIndustryPercentage()==0){System.out.println("-    Aucun pourcentage de surface dédié pour l'industrie");}
                    break;
                case "TREASURY":
                    if(value<0 && scenario.getTreasury()>0){
                        scenario.setTreasury(scenario.getTreasury()+value);
                    }
                    else if(value>0){
                        scenario.setTreasury(scenario.getTreasury()+value);
                    }
                    System.out.println("-    "+value+" pièces d'or");
                    if(scenario.getTreasury()==0){System.out.println("-    Votre trésorerie est vide");}
            }
        }
    }

    private void manageIndustryAndAgricultureCumul(int value, String factor){
        if(scenario.getAgriculturePercentage()+scenario.getIndustryPercentage()==100)return;
        if(value+scenario.getAgriculturePercentage()+scenario.getIndustryPercentage()<=100){
            if(factor.equals("INDUSTRY")){
                scenario.setIndustryPercentage(scenario.getIndustryPercentage()+value);
            }
            else if(factor.equals("AGRICULTURE")){
                scenario.setAgriculturePercentage(scenario.getAgriculturePercentage()+value);
            }
        }
        else {
            int newValue = 100 - (scenario.getAgriculturePercentage() + scenario.getIndustryPercentage());
            if (factor.equals("INDUSTRY")){
                scenario.setIndustryPercentage(scenario.getIndustryPercentage()+newValue);
            }
            else if(factor.equals("AGRICULTURE")){
                scenario.setAgriculturePercentage(scenario.getAgriculturePercentage()+newValue);
            }
        }
    }

    private void printInfosIle(){
        System.out.println("----------------------\n\n");
        System.out.println("Vous avez au total : "+scenario.getTreasury()+" pieces d'or dans votre trésorerie");
        System.out.println("Vous avez au total "+scenario.getFoodUnit()+" unités de nouriture en stock");
        System.out.println("L'agricuture occupe "+scenario.getAgriculturePercentage()+"% de la surface de votre ile");
        System.out.println("L'industrie occupe "+scenario.getIndustryPercentage()+"% de la surface de votre ile");
        System.out.println("Satisfaction gobal de l'ile : "+scenario.getListFactions().getGlobalSatisfactionPercentage()+"\n");
        System.out.println("----------------------\n\n");
    }
}
