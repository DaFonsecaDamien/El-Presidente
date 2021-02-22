package esgi.fr;

public enum Difficulty {
    EASY(0.75f,1.5f,10f),
    NORMAL(1f,1f,20f),
    HARD(1.5f,0.75f,50f);

    private float multiplierGain;
    private float multiplierPerte;
    private float minGlobalSatisfaction;

    Difficulty(float multiplierGain, float multiplierPerte, float minGlobalSatisfaction) {
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

    public float getMinGlobalSatisfaction() {
        return minGlobalSatisfaction;
    }
}
