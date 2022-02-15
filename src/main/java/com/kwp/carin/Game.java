package com.kwp.carin;

import com.kwp.util.CarinRandom;

import java.util.LinkedList;

public class Game extends Thread {
    private static Game instance;
    private final HumanBody humanBody;
    private final float virusSpawnRate;
    private int speedModifier;
    private int antibodyCredit;
    private boolean started;
    private boolean isPlaying;
    private Antibody selectedAntibody;

    private Game() {
        humanBody = HumanBody.getInstance();
        virusSpawnRate = Configuration.getVirusSpawnRate();
        isPlaying = true;
        speedModifier = 1;
        antibodyCredit = Configuration.getInitialAntibodyCredit();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public int getAntibodyCredit() {
        return antibodyCredit;
    }

    private void spawnVirus() {
        if (CarinRandom.nextFloat() < virusSpawnRate) {
            LinkedList<Cell> emptyCells = humanBody.getEmptyCells();
            CarinRandom.nextInt(emptyCells.size());
            Cell cell = emptyCells.get(CarinRandom.nextInt(emptyCells.size()));
            cell.setOrganism(new Virus(GeneticCode.getTest0()));
        }
    }

    private boolean isOver() {
        return !humanBody.hasAntibody() || !humanBody.hasVirus();
    }

    public void run() {
        loop();
        System.out.println("Game is over");
    }

    private void loop() {
        while (true) {
            if (isPlaying) {
                System.out.println("Antibody credit: " + antibodyCredit);
                Organism.runAll();
                spawnVirus();
                humanBody.print();
                try {
                    Thread.sleep(speedModifier * 1000L);
                } catch (InterruptedException ignored) {

                }
            } else {
                Thread.yield();
            }
        }
    }

    public void startGame() {
        try {
            this.start();
            System.out.println("Game started");
        } catch (IllegalThreadStateException e) {
            System.out.println("Game is already started");
        }
    }

    public void resumeGame() {
        isPlaying = true;
        System.out.println("Game resumed");
    }

    public void pauseGame() {
        isPlaying = false;
        System.out.println("Game paused");
    }

    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.start();
    }
}
