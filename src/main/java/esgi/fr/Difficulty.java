package esgi.fr;

import java.io.Serializable;

public enum Difficulty implements Serializable {
    EASY(0.75f, 1.5f, 20),
    NORMAL(1f, 1f, 30),
    HARD(1.5f, 0.75f, 50);

    private final float multiplierPerte;
    private final float multiplierGain;
    private final int minGlobalSatisfaction;

    Difficulty(float multiplierPerte, float multiplierGain, int minGlobalSatisfaction) {
        this.multiplierGain = multiplierGain;
        this.multiplierPerte = multiplierPerte;
        this.minGlobalSatisfaction = minGlobalSatisfaction;
    }

    public float getMultiplierGain() {
        return multiplierGain;
    }

    public float getMultiplierPerte() {
        return multiplierPerte;
    }

    public int getMinGlobalSatisfaction() {return minGlobalSatisfaction;}

}
