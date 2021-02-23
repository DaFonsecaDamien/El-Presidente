package esgi.fr;

import java.util.stream.Stream;

public enum NameFaction {
    CAPITALISTE("CAPITALISTS"),
    COMMUNISTE("COMMUNISTS"),
    LIBERAU("LIBERALS"),
    RELIGIEU("RELIGIOUS"),
    MILITARISTE("MILITARISTS"),
    ECOLOGISTE("ECOLOGISTS"),
    NATIONALISTE("LOYALISTS"),
    LOYALISTE("NATIONALISTS");

    private String value;

    NameFaction(String value) {
        this.value = value;
    }

    // standard getters and setters
    public String getValue(){
        return this.value;
    }
}
