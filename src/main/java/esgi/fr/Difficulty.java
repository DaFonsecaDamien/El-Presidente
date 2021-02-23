package esgi.fr;

import java.io.Serializable;

public enum Difficulty implements Serializable {
    EASY(0.75f, 1.5f),
    NORMAL(1f, 1f),
    HARD(1.5f, 0.75f);

    private final float multiplierPerte;
    private final float multiplierGain;

    Difficulty(float multiplierPerte, float multiplierGain) {
        this.multiplierGain = multiplierGain;
        this.multiplierPerte = multiplierPerte;
    }

    public float getMultiplierGain() {
        return multiplierGain;
    }

    public float getMultiplierPerte() {
        return multiplierPerte;
    }

}
