package esgi.fr.Event;

import esgi.fr.Faction.Faction;
import esgi.fr.Faction.NameFaction;
import esgi.fr.Game;
import esgi.fr.GameParameters.Difficulty;
import esgi.fr.Scenario;
import esgi.fr.managers.FactionManager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Effect implements Serializable {
    private final String typeAction;
    private final Map<String, Integer> actions;

    public Effect(String typeAction, Map<String, Integer> actions) {
        this.typeAction = typeAction;
        this.actions = actions;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public Map<String, Integer> getActions() {
        return actions;
    }

    public void manageEffectSupporters(List<Faction> factions,Difficulty difficulty) {
        int value = this.getActions().get("partisans");

        if (value < 0 && FactionManager.getAllSuportersNumber(factions) > 0) {
            value = (int) (difficulty.getMultiplierPerte() * value);
            FactionManager.setAllSupportersNumberInRandomsFactions(value,factions);
            System.out.println("-    " + (-value) + " citoyens ont quitté l'ile");
        } else if (value > 0) {
            value = (int) (difficulty.getMultiplierGain() * value);
            FactionManager.setAllSupportersNumberInRandomsFactions(value,factions);
            System.out.println("-    " + value + " citoyens ont rejoint votre ile");
        }
        if (FactionManager.getAllSuportersNumber(factions) == 0) {
            System.out.println("-    Aucun citoyens dans votre ile");
        }
    }

    public void manageEffectOnFaction(Difficulty difficulty, List<Faction> factions) {
        for (Map.Entry action : this.getActions().entrySet()) {
            int value = action.getValue().hashCode();
            NameFaction nameFaction = null;
            for (NameFaction faction : NameFaction.values()) {
                if (faction.getValue().equals(action.getKey())) {
                    nameFaction = faction;
                }
            }
            Faction faction = FactionManager.getOneFaction(nameFaction,factions);
            if (value < 0) {
                value = (int) (difficulty.getMultiplierPerte() * value);
                faction.setSatisfactionPercentage(faction.getSatisfactionPercentage() + value);
            } else if (value > 0) {
                value = (int) (difficulty.getMultiplierGain() * value);
                faction.setSatisfactionPercentage(faction.getSatisfactionPercentage() + value);
            }
            System.out.println("-    " + value + "% de satisfaction chez les " + nameFaction + "S");
        }
    }

    public void manageEffectOnFactor(Scenario scenario, Difficulty difficulty) {
        for (Map.Entry action : this.getActions().entrySet()) {
            int value = action.getValue().hashCode();
            switch (action.getKey().toString()) {
                case "AGRICULTURE":
                    if (value < 0 && scenario.getAgriculturePercentage() > 0) {
                        value = (int) (difficulty.getMultiplierPerte() * value);
                        scenario.setAgriculturePercentage(scenario.getAgriculturePercentage() + value);
                    } else if (value > 0) {
                        value = (int) (difficulty.getMultiplierGain() * value);
                        scenario.manageIndustryAndAgricultureCumul(value, "AGRICULTURE",difficulty);
                    }
                    System.out.println("-    " + value + "% de surface dédié à l'agriculture");
                    if (scenario.getAgriculturePercentage() == 0) {
                        System.out.println("-    Aucun pourcentage de surface dédié pour l'agriculture");
                    }
                    break;
                case "INDUSTRY":
                    if (value < 0 && scenario.getIndustryPercentage() > 0) {
                        value = (int) (difficulty.getMultiplierPerte() * value);
                        scenario.setIndustryPercentage(scenario.getIndustryPercentage() + value);
                    } else if (value > 0) {
                        value = (int) (difficulty.getMultiplierGain() * value);
                        scenario.manageIndustryAndAgricultureCumul(value, "INDUSTRY",difficulty);
                    }
                    System.out.println("-    " + value + "% de surface dédié à l'industrie");
                    if (scenario.getIndustryPercentage() == 0) {
                        System.out.println("-    Aucun pourcentage de surface dédié pour l'industrie");
                    }
                    break;
                case "TREASURY":
                    if (value < 0 && scenario.getTreasury() > 0) {
                        value = (int) (difficulty.getMultiplierPerte() * value);
                        scenario.setTreasury(scenario.getTreasury() + value);
                    } else if (value > 0) {
                        value = (int) (difficulty.getMultiplierGain() * value);
                        scenario.setTreasury(scenario.getTreasury() + value);
                    }
                    System.out.println("-    " + value + " pièces d'or");
                    if (scenario.getTreasury() == 0) {
                        System.out.println("-    Votre trésorerie est vide");
                    }
            }
        }
    }
}
