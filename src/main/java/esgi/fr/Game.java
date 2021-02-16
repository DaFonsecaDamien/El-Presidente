package esgi.fr;

import java.util.List;

public class Game {

    private Level level;
    private Mode mode;
    private Scenario scenario;
    private int year;
    private Season season;

    public Game(Level level, Mode mode, Scenario scenario) {
        this.level = level;
        this.mode = mode;
        this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public boolean run(List<Event> events){
        int choice=0;
        for(Event event: events){
            //on vérifie si on a pas perdu lol
            if(isLoose()){
                //afficher les resultats et sortir
                return false;
            }
            //on parcours la list de event
            //TODO demander au user son choix
            //si le choix provoque des evenements ...
            if(event.getChoices().get(choice).getRelatedEvents() != null){
                //...on le parcour! tout en véérifiant si la fonction return pas false pour eviter de boucler pour rien
                if(!run(event.getChoices().get(choice).getRelatedEvents())){
                    return false;
                }
            }
            this.season = season.next();
            if(this.season == Season.WINTER){
                this.year++;
            }
        }
        return true;
    }

    // TODO Condition de perte (francais)
    private boolean isLoose(){

        return false;
    }
}
