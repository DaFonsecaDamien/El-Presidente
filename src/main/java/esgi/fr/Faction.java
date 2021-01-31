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

    @Override
    public void setSatisfaction(int newSatisfaction) {

    }

    @Override
    public void setSupportersNumber(int newSupportersNumber) {

    }

}
