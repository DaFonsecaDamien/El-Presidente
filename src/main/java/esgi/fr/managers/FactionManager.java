package esgi.fr.managers;

import esgi.fr.App;
import esgi.fr.utilities.Faction.Faction;
import esgi.fr.utilities.Faction.NameFaction;
import esgi.fr.Scenario;

import java.util.List;
import java.util.Scanner;

public class FactionManager {

    public static Faction getOneFaction(NameFaction nameFaction,List<Faction> factions) {
        for (Faction faction : factions) {
            if (faction.getNameFaction() == nameFaction) {
                return faction;
            }
        }
        return null;
    }

    public static int getAllSuportersNumber(List<Faction> factions) {
        int somme = 0;
        for (Faction faction : factions) {
            somme += faction.getSupportersNumber();
        }
        return somme;
    }

    public static double getGlobalSatisfactionPercentage(List<Faction> factions) {
        double sommeSatisfactionNumber = 0;
        for (Faction faction : factions) {
            sommeSatisfactionNumber += faction.getSatisfactionPercentage() * faction.getSupportersNumber();
        }

        return sommeSatisfactionNumber / getAllSuportersNumber(factions);

    }

    public static void setAllSatisfaction(int number,List<Faction> factions) {
        for (Faction faction : factions) {
            if (faction.getSatisfactionPercentage() != 0) {
                faction.setSatisfactionPercentage(faction.getSatisfactionPercentage() + number);
            }
        }
    }

    public static void setAllSupportersNumberInRandomsFactions(int numbersOfSupporters,List<Faction> factions) {
        int randomNumberOfSupporters = 1;
        int posOrNeg = numbersOfSupporters > 0 ? 1 : -1;

        while (numbersOfSupporters != 0) {
            randomNumberOfSupporters = (int) (Math.random() * numbersOfSupporters) + posOrNeg;
            numbersOfSupporters -= randomNumberOfSupporters;
            Faction randomFaction = getRandomFaction(factions);
            if (posOrNeg == -1) {
                while (randomFaction.getSupportersNumber() + randomNumberOfSupporters < 0 && getAllSuportersNumber(factions) != 0) {
                    randomNumberOfSupporters = randomNumberOfSupporters + randomFaction.getSupportersNumber();
                    randomFaction.setSupportersNumber(0);
                    randomFaction.setSatisfactionPercentage(0);
                    randomFaction = getRandomFaction(factions);
                }
            }
            randomFaction.setSupportersNumber(randomFaction.getSupportersNumber() + randomNumberOfSupporters);
        }
    }

    public static void killPeople(List<Faction> factions, Scenario scenario) {
        Faction randomFaction;
        System.out.println(" Vos citoyens meurent de faim ... !\n");
        int supportersBeforeStarving = FactionManager.getAllSuportersNumber(factions);
        do {
            do{
                randomFaction = FactionManager.getRandomFaction(factions);
            }while(randomFaction.getSupportersNumber()<1);
            randomFaction.setSupportersNumber(randomFaction.getSupportersNumber() - 1);

            FactionManager.setAllSatisfaction(-2,factions);

        } while (scenario.getFoodUnit() + 40 * scenario.getAgriculturePercentage() < FactionManager.getAllSuportersNumber(factions));
        int supportersAfterStarving = supportersBeforeStarving - FactionManager.getAllSuportersNumber(factions);
        System.out.println(" La famine est terrible vous avez perdu " + supportersAfterStarving + " citoyen(s)  !\n");
    }

    public static void increasePeople(List<Faction> factions) {
        System.out.println(" Si bien que la natalité de votre patrie augmente !\n");
        int randomPercentage = (int) (Math.random() * 10) + 1;
        int totalNumbersOfSuportersToAdd = randomPercentage * FactionManager.getAllSuportersNumber(factions) / 100;

        FactionManager.setAllSupportersNumberInRandomsFactions(totalNumbersOfSuportersToAdd,factions);
    }




    public static Faction getRandomFaction(List<Faction> factions) {
        int randomFaction = (int) (Math.random() * 8) + 1;
        return chooseFaction(randomFaction,factions);
    }

    public static Faction chooseFaction(int factionChosen,List<Faction> factions) {

        NameFaction nameFactionChosen = NameFaction.CAPITALISTE;

        switch (factionChosen) {
            case 1:
                nameFactionChosen = NameFaction.CAPITALISTE;
                break;
            case 2:
                nameFactionChosen = NameFaction.COMMUNISTE;
                break;
            case 3:
                nameFactionChosen = NameFaction.LIBERAU;
                break;
            case 4:
                nameFactionChosen = NameFaction.RELIGIEU;
                break;
            case 5:
                nameFactionChosen = NameFaction.MILITARISTE;
                break;
            case 6:
                nameFactionChosen = NameFaction.ECOLOGISTE;
                break;
            case 7:
                nameFactionChosen = NameFaction.LOYALISTE;
                break;
            case 8:
                nameFactionChosen = NameFaction.NATIONALISTE;
                break;
        }
        return getOneFaction(nameFactionChosen,factions);
    }

    public static int getChoiceFaction(List<Faction> factions,int index) {
        int choice = 0;
        Scanner sc = new Scanner(System.in);

        printInfosFactions(factions);
        System.out.println("choisissez celle que vous voulez soudoyer");
        while (choice < 1 || choice > factions.size()) {
            if (sc.next().equals("Q")) {
                App.menuQuitGame(index);
            }
            while (!sc.hasNextInt()) {
                sc = new Scanner(System.in);
            }
            choice = sc.nextInt();
        }

        return choice;
    }

    public static void printInfosFactions(List<Faction> factions) {
        int i = 0;
        System.out.println("Voici la liste des factions : ");
        for (Faction faction : factions) {
            i++;
            System.out.println(i + " - ");
            System.out.println(faction);
            System.out.println("Or requis pour soudoyer : " + faction.getSupportersNumber() * 15 + "\n");
        }
    }
}
