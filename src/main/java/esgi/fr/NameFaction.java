package esgi.fr;

import java.util.stream.Stream;

public enum NameFaction {
    CAPITALISTE("Capitalists"),
    COMMUNISTE("Communists"),
    LIBERAU("Liberals"),
    RELIGIEU("Religious"),
    MILITARISTE("Militarists"),
    ECOLOGISTE("Ecologists"),
    NATIONALISTE("Nationalists"),
    LOYALISTE("Loyalists");

    public final String label;

    NameFaction(String label) {
        this.label = label;
    }

}
