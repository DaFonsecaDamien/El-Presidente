package esgi.fr;

public enum Difficulty {
    EASY(0.75f,1.5f),
    NORMAL(1f,1f),
    HARD(1.5f,0.75f);

    private float multiplierGain;
    private float multiplierPerte;

    Difficulty(float multiplierGain, float multiplierPerte) {
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
