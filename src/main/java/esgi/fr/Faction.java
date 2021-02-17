package esgi.fr;

public class Faction implements ManageFactions{

    private NameFaction nameFaction;
    private int satisfaction;
    private int supportersNumber;

    public Faction(NameFaction nameFaction, int satisfaction, int supportersNumber) {
        this.nameFaction = nameFaction;
        this.satisfaction = satisfaction;
        this.supportersNumber = supportersNumber;
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

    public void setNameFaction(NameFaction nameFaction) {
        this.nameFaction = nameFaction;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    public void setSupportersNumber(int supportersNumber) {
        this.supportersNumber = supportersNumber;
    }

    @Override
    public void bribeFaction() {

    }
}
