package esgi.fr;

import java.util.List;

public class ListFaction {
    private List<Faction> factions;

    public ListFaction(List<Faction> factions) {
        this.factions = factions;
    }

    public List<Faction> getFactions() {
        return factions;
    }

    public Faction getOneFaction(NameFaction nameFaction){
        for(Faction faction : factions){
            if(faction.getNameFaction() == nameFaction){
                return faction;
            }
        }
        return null;
    }

    public int getAllSuportersNumber(){
        int somme = 0;
        for(Faction faction : factions){
            somme+= faction.getSupportersNumber();
        }
        return somme;
    }

    public double getGlobalSatisfactionPercentage(){
        double sommeSatisfactionNumber = 0;
        for(Faction faction : factions){
            sommeSatisfactionNumber += faction.getSatisfactionPercentage() * faction.getSupportersNumber();
        }

        return sommeSatisfactionNumber / getAllSuportersNumber();

    }

    public void setAllSatisfaction(int number){
        for(Faction faction : factions){
            if(faction.getSatisfactionPercentage()!=0){
                faction.setSatisfactionPercentage(faction.getSatisfactionPercentage() + number);
            }
        }
    }

    public void setAllSupportersNumberInRandomsFactions(int numbersOfSupporters){
        int randomNumberOfSupporters=1;
        int posOrNeg = numbersOfSupporters > 0 ? 1 : -1;

        while (numbersOfSupporters != 0 ){
            randomNumberOfSupporters = (int)(Math.random() * numbersOfSupporters) + posOrNeg;
            numbersOfSupporters -= randomNumberOfSupporters;
            Faction randomFaction = getRandomFaction();
            if(posOrNeg==-1){
                while(randomFaction.getSupportersNumber() + randomNumberOfSupporters<0 && getAllSuportersNumber()!=0){
                    randomNumberOfSupporters = randomNumberOfSupporters + randomFaction.getSupportersNumber();
                    randomFaction.setSupportersNumber(0);
                    randomFaction.setSatisfactionPercentage(0);
                    randomFaction = getRandomFaction();
                }
            }
            randomFaction.setSupportersNumber(randomFaction.getSupportersNumber() + randomNumberOfSupporters);
        }
    }

    public void addSpportersInFactions(int numbersOfSupportersToAdd){
        setAllSupportersNumberInRandomsFactions(numbersOfSupportersToAdd);
    }

    public void removeSpportersInFactions(int numbersOfSupportersToRemove){
        setAllSupportersNumberInRandomsFactions(numbersOfSupportersToRemove);
    }

    public Faction getRandomFaction(){
        int randomFaction = (int)(Math.random() * 8) + 1;
        Faction factionChosen = chooseFaction(randomFaction);
        return factionChosen;
    }

    public Faction chooseFaction(int factionChosen){

        NameFaction nameFactionChosen = NameFaction.CAPITALISTE;

        switch (factionChosen){
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
                nameFactionChosen = NameFaction.NATIONALISTE;
                break;
            case 8:
                nameFactionChosen = NameFaction.LOYALISTE;
                break;
        }
        return getOneFaction(nameFactionChosen);
    }

}
