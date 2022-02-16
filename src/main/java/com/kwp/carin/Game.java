package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.util.CarinRandom;

import java.util.LinkedList;

public class Game extends Thread {
    private static Game instance;
    private final HumanBody humanBody;
    private final float virusSpawnRate;
    private int speedModifier;
    private int antibodyCredit;
    private final int antibodyPlacementCost;
    private boolean started;
    private boolean isPlaying;
    private Antibody selectedAntibody;

    private Game() {
        humanBody = HumanBody.getInstance();
        virusSpawnRate = Configuration.getVirusSpawnRate();
        antibodyCredit = Configuration.getInitialAntibodyCredit();
        antibodyPlacementCost = Configuration.getAntibodyPlacementCost();
        isPlaying = true;
        speedModifier = 1;
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

    public HumanBody getHumanBody() {
        return humanBody;
    }

    public void buyAntibody(int i, int j) {
        Cell target = humanBody.getCell(i, j);
        if (antibodyCredit >= antibodyPlacementCost && target != null && target.isEmpty()) {
            antibodyCredit -= antibodyPlacementCost;
            target.setOrganism(new Antibody(GeneticCode.getDefault()));
            started = true;
        }
    }

    private void spawnVirus() {
        if (CarinRandom.nextFloat() < virusSpawnRate) {
            LinkedList<Cell> emptyCells = humanBody.getEmptyCells();
            if (emptyCells.size() > 0) {
                CarinRandom.nextInt(emptyCells.size());
                Cell cell = emptyCells.get(CarinRandom.nextInt(emptyCells.size()));
                cell.setOrganism(Virus.getRandomVirus());
                started = true;
            }
        }
    }

    private boolean isOver() {
        return  (Virus.amount() == 0 || Antibody.amount() == 0) && started;
    }

    public void run() {
        loop();
        System.out.println("Game is over");
    }

    private void loop() {
        while (true) {
            if (isPlaying) {
                Organism.wakeAll();
                Organism.runAll();
                spawnVirus();
                System.out.println("Antibody credit: " + antibodyCredit);
                System.out.println("Virus left: " + Virus.amount());
                System.out.println("Antibody left: " + Antibody.amount());
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
}
