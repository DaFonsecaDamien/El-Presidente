package esgi.fr.utilities.Faction;


public enum NameFaction {
    CAPITALISTE("CAPITALISTS"),
    COMMUNISTE("COMMUNISTS"),
    LIBERAU("LIBERALS"),
    RELIGIEU("RELIGIOUS"),
    MILITARISTE("MILITARISTS"),
    ECOLOGISTE("ECOLOGISTS"),
    NATIONALISTE("LOYALISTS"),
    LOYALISTE("NATIONALISTS");

    private final String value;

    NameFaction(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
