package esgi.fr;

public class Faction implements ManageFactions{

    public static double globalSatisfaction; // Public ??
    private NameFaction nameFaction;
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

    public int getSupportersNumber() {
        return supportersNumber;
    }

    public void setNameFaction(NameFaction nameFaction) {
        this.nameFaction = nameFaction;
    }

    public void setSatisfactionPercentage(int satisfactionPercentage) {
        this.satisfactionPercentage = satisfactionPercentage;
    }

    public void setSupportersNumber(int supportersNumber) {
        this.supportersNumber = supportersNumber;
    }

    @Override
    public boolean bribeFaction(int treasurer) {
        if(satisfactionPercentage >99 || supportersNumber*15>treasurer){
            return false;
        }
        satisfactionPercentage += 10;
        return true;
    }

    @Override
    public String toString() {
        return "Nom : " + nameFaction +
                "\nPourcentage de satisfaction : " + satisfactionPercentage +
                "\nNombre de partisans : " + supportersNumber;
    }

    public double calculGlobalSatisfaction(){

        
        return globalSatisfaction;

    }
}
