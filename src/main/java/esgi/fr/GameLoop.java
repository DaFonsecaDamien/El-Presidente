package esgi.fr;

public class GameLoop implements Runnable {
    private Scenario scenario;

    public GameLoop(Scenario scenario) {
        this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }

    private boolean running = false;

    public void run(){

        running = true;

    }


}
