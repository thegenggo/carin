package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.util.CarinRandom;

import java.util.LinkedList;

public class Game extends Thread {
    private static Game instance;
    private final HumanBody humanBody;
    private final float virusSpawnRate;
    private int antibodyCredit;
    private final int antibodyPlacementCost;
    private final int antibodyMoveCost;
    private int speedModifier;
    private boolean started;
    private boolean isPlaying;
    private Antibody selectedAntibody;

    private Game() {
        humanBody = HumanBody.getInstance();
        virusSpawnRate = Configuration.getVirusSpawnRate();
        antibodyCredit = Configuration.getInitialAntibodyCredit();
        antibodyPlacementCost = Configuration.getAntibodyPlacementCost();
        antibodyMoveCost = Configuration.getAntibodyMoveCost();
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
            Antibody antibody = new Antibody(GeneticCode.getBerserk());
            antibodyCredit -= antibodyPlacementCost;
            target.setOrganism(antibody);
            antibody.setCell(target);
            started = true;
        }
    }

    public void selectAntibody(int i, int j) {
        Cell cell = humanBody.getCell(i, j);
        if (cell.isEmpty()) return;
        Organism organism = cell.getOrganism();
        if (organism.isAntibody()) {
            if (selectedAntibody != null) selectedAntibody.setSelected(false);
            selectedAntibody = (Antibody) organism;
            selectedAntibody.setSelected(true);
        }
    }

    public void moveSelectedAntibody(int i, int j) {
        if (selectedAntibody != null) {
            Cell target = humanBody.getCell(i, j);
            if (target != null && target.isEmpty() && selectedAntibody.isReady()) {
                Cell currentCell = selectedAntibody.getCell();
                currentCell.clear();
                selectedAntibody.setCell(target);
                target.setOrganism(selectedAntibody);
                selectedAntibody.receiveDamage(antibodyMoveCost);
                selectedAntibody.setReady(false);
            }
        }
    }

    private void spawnVirus() {
        if (CarinRandom.nextFloat() < virusSpawnRate) {
            LinkedList<Cell> emptyCells = humanBody.getEmptyCells();
            if (emptyCells.size() > 0) {
                Virus virus = Virus.getRandomVirus();
                Cell cell = emptyCells.get(CarinRandom.nextInt(emptyCells.size()));
                cell.setOrganism(virus);
                virus.setCell(cell);
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
                Organism.runAll();
                spawnVirus();
                System.out.println("Antibody credit: " + antibodyCredit);
                System.out.println("Virus left: " + Virus.amount());
                System.out.println("Antibody left: " + Antibody.amount());
                humanBody.print();
                Organism.wakeAll();
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
