package esgi.fr;

public class Faction implements ManageFactions{

    private String name;
    private int satisfactionPercentage;
    private int numberOfPartisans;

    public Faction(String name, int satisfactionPercentage, int numberOfPartisans) {
        this.name = name;
        this.satisfactionPercentage = satisfactionPercentage;
        this.numberOfPartisans = numberOfPartisans;
    }

    @Override
    public void setSatisfaction(int newSatisfaction) {

    }

    @Override
    public void setSupportersNumber(int newSupportersNumber) {

    }

}
