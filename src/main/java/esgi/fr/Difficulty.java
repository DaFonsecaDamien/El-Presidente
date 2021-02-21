package esgi.fr;

public enum Difficulty {
    EASY(0.5f,10f),
    NORMAL(1f,20f),
    HARD(2f,50f);

    private float multiplier;
    private float minGlobalSatisfaction;

    Difficulty(float multiplier, float minGlobalSatisfaction) {
        this.multiplier = multiplier;
        this.minGlobalSatisfaction = minGlobalSatisfaction;
    }

    public float getMultiplier(){
        return multiplier;
    }

    public double getMinGlobalSatisfaction(){
        return minGlobalSatisfaction;
    }
}
