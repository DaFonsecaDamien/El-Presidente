package esgi.fr;

import java.io.Serializable;

public class Faction implements ManageFactions, Serializable {
    private final NameFaction nameFaction;
    private int satisfactionPercentage;
    private int supportersNumber;


    public Faction(NameFaction nameFaction, int satisfaction, int supportersNumber) {
        this.nameFaction = nameFaction;
        this.satisfactionPercentage = satisfaction;
        this.supportersNumber = supportersNumber;
    }

    public NameFaction getNameFaction() {
        return nameFaction;
    }

    public int getSatisfactionPercentage() {
        return satisfactionPercentage;
    }

    public void setSatisfactionPercentage(int satisfactionPercentage) {
        if (satisfactionPercentage < 0) {
            this.satisfactionPercentage = 0;
            return;
        }
        if (satisfactionPercentage > 100) {
            this.satisfactionPercentage = 100;
            return;
        }
        this.satisfactionPercentage = satisfactionPercentage;
    }

    public int getSupportersNumber() {
        return supportersNumber;
    }

    public void setSupportersNumber(int supportersNumber) {
        this.supportersNumber = supportersNumber;
    }

    @Override
    public boolean bribeFaction() {
        if (satisfactionPercentage > 99 || satisfactionPercentage < 1 || nameFaction == NameFaction.LOYALISTE) {
            return false;
        }
        satisfactionPercentage += 10;
        return true;
    }

    @Override
    public String toString() {
        return "\n\nNom : " + nameFaction +
                "\nSatisfaction : " + satisfactionPercentage + "%" +
                "\nNombre de partisans : " + supportersNumber;
    }
}
