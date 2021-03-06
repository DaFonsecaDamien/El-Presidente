package esgi.fr;

import esgi.fr.Event.Event;
import esgi.fr.Faction.Faction;
import esgi.fr.Faction.NameFaction;
import esgi.fr.GameParameters.Difficulty;
import esgi.fr.GameParameters.Season;
import esgi.fr.managers.FactionManager;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class Scenario implements Serializable {
    private final List<Event> events;
    private final String name;
    private final String story;
    private int treasury;
    private int foodUnit;
    private int industryPercentage;
    private int agriculturePercentage;
    private final List<Faction> factions;
    private int year;
    private Season season;

    public Scenario(List<Event> events, String name, String story, int agriculturePercentage, int industryPercentage, int treasury, int foodUnit, List<Faction> factions) {
        this.events = events;
        this.name = name;
        this.story = story;
        this.treasury = treasury;
        this.foodUnit = foodUnit;
        this.industryPercentage = industryPercentage;
        this.agriculturePercentage = agriculturePercentage;
        this.factions = factions;
        this.year = 1;
        this.season = Season.WINTER;
    }

    //Getter
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public List<Event> getEvents() {
        return events;
    }

    public String getName() {
        return name;
    }

    public String getStory() {
        return story;
    }


    //Setter
    public int getTreasury() {
        return treasury;
    }

    public void setTreasury(int treasury) {
        if (treasury < 0) {
            this.treasury = 0;
            return;
        }
        this.treasury = treasury;
    }

    public int getIndustryPercentage() {
        return industryPercentage;
    }

    public void setIndustryPercentage(int industryPercentage) {
        if (industryPercentage < 0) {
            this.industryPercentage = 0;
            return;
        }
        this.industryPercentage = industryPercentage;
    }

    public int getAgriculturePercentage() {
        return agriculturePercentage;
    }

    public void setAgriculturePercentage(int agriculturePercentage) {
        if (agriculturePercentage < 0) {
            this.agriculturePercentage = 0;
            return;
        }
        this.agriculturePercentage = agriculturePercentage;
    }

    public int getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(int foodUnit) {
        this.foodUnit = foodUnit;
    }

    public List<Faction> getListFactions() {
        return factions;
    }

    // ***** //

    public void manageYear() {
        if (season== Season.WINTER) {
            System.out.println("\n\nNous voici en fin d'année !\n\n");

            manageIndustry();
            printInfosIle();
            FactionManager.printInfosFactions(factions);
            bribeFactionMenu();
            marketPlace();
            yearBilan();
            setYear(year + 1);
        }
        System.out.println("Annee numero : " + year);
        System.out.println("Saison " + season);
    }

    private void marketPlace() {
        String choice = "";
        Scanner sc = new Scanner(System.in);

        System.out.println("Vous pouvez achetez des unites de nouritures pour subvenir aux besoins");
        System.out.println("des citoyens de votre ile\n");
        System.out.println("Vous avez " + FactionManager.getAllSuportersNumber(factions) + " citoyens dans votre ile");
        System.out.println("Vous avez " + foodUnit+ " unités de nourriture\n");
        System.out.println("Soyez prévoyant !\n");

        System.out.println("Voulez vous achetez des unités de nourriture ?");
        System.out.println("Cette opération vous coutera 8 or par unité\n");
        System.out.println("Si oui tapez sur 'o' sinon tapez sur 'n'\n");

        do {
            choice = sc.nextLine();
            if (choice.equals("Q")) {
                App.menuQuitGame();
            }
        } while (!choice.equals("o") && !choice.equals("n"));

        if (choice.equals("o")) {
            buyFoodUnits();
        }
    }

    private void buyFoodUnits() {
        int nbFoodUnitBought = 0;
        Scanner sc = new Scanner(System.in);
        int nbUnitFoodMaxPossible = treasury / 8;
        System.out.println("Combien d'unités de nourriture voulez vous acheter ?");

        while (nbFoodUnitBought < 1 || nbFoodUnitBought > nbUnitFoodMaxPossible) {
            if (sc.next().equals("Q")) {
                App.menuQuitGame();
            }
            System.out.println("Vous pouvez achetez jusqu'a " + nbUnitFoodMaxPossible + " unités de nourriture\n");
            while (!sc.hasNextInt()) {
                sc = new Scanner(System.in);
            }
            nbFoodUnitBought = sc.nextInt();
        }
        setTreasury(treasury - 8 * nbFoodUnitBought);
        setFoodUnit(foodUnit + nbFoodUnitBought);

        System.out.println("Vous avez acheté " + nbFoodUnitBought + " unités de nourriture !");
        System.out.println("Vous avez dépensé au total " + nbFoodUnitBought * 8 + " pièces d'or\n");

    }

    public void manageIndustryAndAgricultureCumul(int value, String factor, Difficulty difficulty) {
        if (agriculturePercentage + industryPercentage == 100) return;
        if (value + agriculturePercentage + industryPercentage <= 100) {
            if (factor.equals("INDUSTRY")) {
                setIndustryPercentage(industryPercentage+ (int) (difficulty.getMultiplierGain() * value));
            } else if (factor.equals("AGRICULTURE")) {
                setAgriculturePercentage(agriculturePercentage + (int) (difficulty.getMultiplierGain() * value));
            }
        } else {
            int newValue = 100 - (agriculturePercentage + industryPercentage);
            if (factor.equals("INDUSTRY")) {
               setIndustryPercentage(industryPercentage + (int) (difficulty.getMultiplierGain() * newValue));
            } else if (factor.equals("AGRICULTURE")) {
                setAgriculturePercentage(agriculturePercentage + (int) (difficulty.getMultiplierGain() * newValue));
            }
        }
    }

    private void printInfosIle() {
        System.out.println("----------------------\n\n");
        System.out.println("Vous avez au total : " + treasury + " pieces d'or dans votre trésorerie");
        System.out.println("Vous avez au total " + foodUnit + " unités de nouriture en stock");
        System.out.println("L'agricuture occupe " + agriculturePercentage + "% de la surface de votre ile");
        System.out.println("L'industrie occupe " + industryPercentage + "% de la surface de votre ile");
        System.out.println("Satisfaction gobal de l'ile : " + FactionManager.getGlobalSatisfactionPercentage(factions) + "\n");
        System.out.println("----------------------\n\n");
    }

    public void manageAgriculture() {
        int foodUnitConsumed = FactionManager.getAllSuportersNumber(factions);
        if (foodUnitConsumed > 0) {
            this.setFoodUnit(this.getFoodUnit() - foodUnitConsumed);
            System.out.println("Votre ile a consommé " + foodUnitConsumed + " unités de nourriture");
        }
        if (this.getAgriculturePercentage() > 0) {
            int gainFood = 10 * this.getAgriculturePercentage();
            this.setFoodUnit(this.getFoodUnit() + gainFood);
            System.out.println("Voici ce que l'ile vous a rapporté en nouriture cette saison: " + gainFood);
        }
    }

    public void manageIndustry() {
        if (this.getIndustryPercentage() > 0) {
            int gainOr = 10 * this.getIndustryPercentage();
            this.setTreasury(this.getTreasury() + gainOr);
            System.out.println("Voic ce que l'ile vous rapporte en or cette année: " + gainOr);
        }
    }

    private void bribeFactionMenu() {

        int choiceFaction = 0;
        String accept = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Voulez vous soudoyer une faction ?");
            System.out.println("Si oui tapez sur 'o' sinon tapez sur 'n'");
            if (accept.equals("Q")) {
                App.menuQuitGame();
            }
            accept = sc.nextLine();

            if (accept.equals("o")) {
                choiceFaction = FactionManager.getChoiceFaction(factions);
                Faction factionChosen = FactionManager.chooseFaction(choiceFaction,factions);
                manageResultBribe(factionChosen);
            }

        } while (!accept.equals("n"));

    }

    private void manageResultBribe(Faction factionChosen) {

        if (factionChosen.getSupportersNumber() * 15 > getTreasury()) {
            System.out.println("Pas assez d'argent");
            return;
        }

        System.out.println("Vous avez choisi de soudoyer la faction des " + factionChosen.getNameFaction() + "S");
        if (factionChosen.bribeFaction()) {
            setTreasury(getTreasury() - 15 * factionChosen.getSupportersNumber());
            System.out.println("Leur satisfaction a auguementé de 10%!\n");

            Faction loyalistFaction = FactionManager.getOneFaction(NameFaction.LOYALISTE,factions);
            loyalistFaction.setSatisfactionPercentage(loyalistFaction.getSatisfactionPercentage() - (factionChosen.getSupportersNumber() * 15) / 10);
            System.out.println("La satisfaction de la faction loyaliste est desormais de " + loyalistFaction.getSatisfactionPercentage() + "%\n");
        } else {
            System.out.println("Vous n'etes pas en meusure de soudoyer cette faction\n");
        }
    }

    public void yearBilan() {
        System.out.println("C'est l'heure du bilan de fin d'année ! \n");

        if (this.getFoodUnit() + 40 * this.getAgriculturePercentage() < FactionManager.getAllSuportersNumber(factions)) {
            System.out.println(" Malheureusement vous n'avez pas de quoi nourrir tous vos citoyens ...  \n");
            killPeople();
        } else if (40 * this.getAgriculturePercentage() >= FactionManager.getAllSuportersNumber(factions) * 1.10) {
            System.out.println(" Bonne nouvelle : votre agricultre se porte bien  !\n");
            increasePeople();
        }
    }

    public void killPeople() {
        Faction randomFaction;
        System.out.println(" Vos citoyens meurent de faim ... !\n");
        int supportersBeforeStarving = FactionManager.getAllSuportersNumber(factions);
        do {
            do{
                randomFaction = FactionManager.getRandomFaction(factions);
            }while(randomFaction.getSupportersNumber()<1);
            randomFaction.setSupportersNumber(randomFaction.getSupportersNumber() - 1);

            FactionManager.setAllSatisfaction(-2,factions);

        } while (this.getFoodUnit() + 40 * this.getAgriculturePercentage() < FactionManager.getAllSuportersNumber(factions));
        int supportersAfterStarving = supportersBeforeStarving - FactionManager.getAllSuportersNumber(factions);
        System.out.println(" La famine est terrible vous avez perdu " + supportersAfterStarving + " citoyen(s)  !\n");
    }

    public void increasePeople() {
        System.out.println(" Si bien que la natalité de votre patrie augmente !\n");
        int randomPercentage = (int) (Math.random() * 10) + 1;
        int totalNumbersOfSuportersToAdd = randomPercentage * FactionManager.getAllSuportersNumber(factions) / 100;

        FactionManager.setAllSupportersNumberInRandomsFactions(totalNumbersOfSuportersToAdd,factions);
    }

    @Override
    public String toString() {
        return name + "\n\n" +
                story + "\n\n" +
                "Or dans votre trésorerie : " + treasury + "\n" +
                "Unité de nourritures en stock : " + foodUnit + "\n" +
                "Surface de l'ile dédié à l'industrie : " + industryPercentage + "%\n" +
                "Surface de l'ile dédié à l'agriculture : " + agriculturePercentage + "%\n" +
                "Factions : " + factions + "\n" +
                "Nous somme en Hiver\n";
    }
}
