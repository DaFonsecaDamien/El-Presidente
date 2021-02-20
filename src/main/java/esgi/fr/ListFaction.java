package esgi.fr;

import java.util.List;

public class ListFaction {
    private List<Faction> factions;

    public ListFaction(List<Faction> factions) {
        this.factions = factions;
    }

    public List<Faction> getFactions() {
        return factions;
    }

    public Faction getOneFaction(NameFaction nameFaction){
        for(Faction faction : factions){
            if(faction.getNameFaction() == nameFaction){
                return faction;
            }
        }
        return null;
    }

    public int getAllSupotersNumber(){
        int somme = 0;
        for(Faction faction : factions){
            somme+= faction.getSupportersNumber();
        }
        return somme;
    }
}
