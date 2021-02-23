package esgi.fr;

public enum Season {
    WINTER,
    SPRING,
    SUMMER,
    AUTUMN {
        @Override
        public Season next() {
            return values()[0];
        }

    };

    public Season next() {
        // No bounds checking required here, because the last instance overrides
        return values()[ordinal() + 1];
    }
}
