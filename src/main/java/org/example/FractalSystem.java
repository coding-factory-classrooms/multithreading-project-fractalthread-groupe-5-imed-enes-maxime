package org.example;

public abstract class FractalSystem {
    protected final int maxIteration;
    protected final int[] colors;
    protected final int nbThread;

    public FractalSystem(int maxIteration, int nbThread){
        this.maxIteration = maxIteration;
        this.colors = FractalUtil.getColors(maxIteration);

        this.nbThread = nbThread;
    }
}
