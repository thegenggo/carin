package com.kwp.carin;

import com.kwp.util.CarinRandom;

public class Game extends Thread {
    private static Game instance;
    private final HumanBody humanBody;
    private final float virusSpawnRate;
    private int speedModifier;
    private int antibodyCredit;
    private Antibody selectedAntibody;

    private Game() {
        Configuration configuration = Configuration.getInstance();
        humanBody = HumanBody.getInstance();
        virusSpawnRate = configuration.getVirusSpawnRate();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private void spawnVirus() {
        if (CarinRandom.nextFloat() < virusSpawnRate) {

        }
    }

    public void run() {

    }
}
