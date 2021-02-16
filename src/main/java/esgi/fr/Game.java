package esgi.fr;

public class Game {

    private Level level;
    private Mode mode;
    private Scenario scenario;

    public Game(Scenario scenario) {
        this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }
}
