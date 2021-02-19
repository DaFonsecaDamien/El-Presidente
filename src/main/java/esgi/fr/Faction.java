package esgi.fr;

public class Faction implements ManageFactions{

    public static double globalSatisfaction;
    private NameFaction nameFaction;
    private int satisfaction;
    private int supportersNumber;

    public Faction(NameFaction nameFaction, int satisfaction, int supportersNumber) {
        this.nameFaction = nameFaction;
        this.satisfaction = satisfaction;
        this.supportersNumber = supportersNumber;
    }

    @Override
    public void setSatisfaction(int newSatisfaction) {

    }

    @Override
    public void setSupportersNumber(int newSupportersNumber) {

    }

    public NameFaction getNameFaction() {
        return nameFaction;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public int getSupportersNumber() {
        return supportersNumber;
    }

    public double calculGlobalSatisfaction(){

        return globalSatisfaction;
    }
}
