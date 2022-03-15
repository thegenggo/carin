package com.kwp.carin;

import com.kwp.util.CarinRandom;

import java.util.LinkedList;

public class Game extends Thread {
    private static Game instance;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private final HumanBody humanBody;
    private final float virusSpawnRate;
    private int antibodyCredit;
    private final int antibodyPlacementCost;
    private final int antibodyMoveCost;
    private float speedModifier;
    private boolean started;
    private boolean isPlaying;
    private Antibody selectedAntibody;
    private final float[] SPEED_RANGE = {0.5f, 1.0f, 1.5f, 2.0f};
    private int currentSpeedIndex;
    private final boolean DEBUG = false;
    private final int maximunVirusSpawn = 1;

    private Game() {
        humanBody = HumanBody.getInstance();
        virusSpawnRate = Configuration.getVirusSpawnRate();
        antibodyCredit = Configuration.getInitialAntibodyCredit();
        antibodyPlacementCost = Configuration.getAntibodyPlacementCost();
        antibodyMoveCost = Configuration.getAntibodyMoveCost();
        isPlaying = false;
        currentSpeedIndex = 1;
        speedModifier = SPEED_RANGE[currentSpeedIndex];
    }

    public int getAntibodyCredit() {
        return antibodyCredit;
    }

    public HumanBody getHumanBody() {
        return humanBody;
    }

    /** @return 1 if buying success
     *          -1 if target cell is not empty
     *          0 if antibody credit not enough */
    public int buyAntibody(int i, int j, Antibody.Type type) {
        if (antibodyCredit >= antibodyPlacementCost) {
            Cell target = humanBody.getCell(i, j);
            if (target != null && target.isEmpty()) {
                Antibody antibody = Antibody.getInstance(type);
                antibodyCredit -= antibodyPlacementCost;
                target.setOrganism(antibody);
                antibody.setCell(target);
                started = true;
                return 1;
            } else {
                return -1;
            }
        }
        return 0;
    }

    public void selectAntibody(int i, int j) {
        Cell cell = humanBody.getCell(i, j);
        if (cell.isEmpty()) return;
        Organism organism = cell.getOrganism();
        if (organism.isAntibody()) {
            if (selectedAntibody != null) selectedAntibody.setSelected(false);
            selectedAntibody = (Antibody) organism;
            selectedAntibody.setSelected(true);
            if(DEBUG) System.out.println("Selected antibody: " + selectedAntibody);
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
                selectedAntibody.setSelected(false);
                selectedAntibody = null;
            }
        }
    }

    public void decideMove(int i, int j) {
        if (selectedAntibody != null) {
            moveSelectedAntibody(i, j);
        } else {
            selectAntibody(i, j);
        }
    }

    private void spawnVirus() {
        if (/*Virus.getSpawned() < maximunVirusSpawn &&*/ CarinRandom.nextFloat() < virusSpawnRate) {
            LinkedList<Cell> emptyCells = humanBody.getEmptyCells();
            if (emptyCells.size() > 0) {
                Virus virus = Virus.getRandomVirus();
                Cell cell = emptyCells.get(CarinRandom.nextInt(emptyCells.size()));
                cell.setOrganism(virus);
                virus.setCell(cell);
            }
        }
        if (Virus.amount() > 20) started = true;
    }

    public void increaseAntibodyCredit(int amount) {
        antibodyCredit += amount;
    }

    public float increaseSpeed() {
        if (currentSpeedIndex < SPEED_RANGE.length - 1) {
            currentSpeedIndex++;
        }
        return speedModifier = SPEED_RANGE[currentSpeedIndex];
    }

    public float decreaseSpeed() {
        if (currentSpeedIndex > 0) {
            currentSpeedIndex--;
        }
        return speedModifier = SPEED_RANGE[currentSpeedIndex];
    }

    public boolean isGameOver() {
        return  (Virus.amount() == 0 || Antibody.amount() == 0) && started;
    }

    public int checkWin() {
        if (!started) return 0;
        if (Virus.amount() == 0) {
            isPlaying = false;
            return 1; // antibody win
        } else if (Antibody.amount() == 0) {
            isPlaying = false;
            return 2; // virus win
        } else {
            return 0; // no win
        }
    }

    public void startGame() {
        try {
            start();
            isPlaying = true;
            if(DEBUG) System.out.println("Game started");
        } catch (IllegalThreadStateException e) {
            if(DEBUG) System.out.println("Game is already started");
        }
        isPlaying = true;
    }

    public void resumeGame() {
        currentSpeedIndex = 1;
        speedModifier = SPEED_RANGE[currentSpeedIndex];
        isPlaying = true;
        if(DEBUG) System.out.println("Game resumed");
    }

    public void pauseGame() {
        isPlaying = false;
        if(DEBUG) System.out.println("Game paused");
    }

    public void run() {
        loop();
        if(DEBUG) System.out.println("Game is over");
    }

    public void resetGame() {
        humanBody.reset();
        antibodyCredit = Configuration.getInitialAntibodyCredit();
        currentSpeedIndex = 1;
        speedModifier = SPEED_RANGE[currentSpeedIndex];
        started = false;
        Organism.reset();
        isPlaying = false;
        if(DEBUG) System.out.println("Game reset");
    }

    private void loop() {
        long lastTime = 0;
        while (true) {
            long currentTime = System.currentTimeMillis();
            if (isPlaying && currentTime - lastTime >= 1000 / speedModifier) {
                lastTime = currentTime;
                Organism.runAll();
                spawnVirus();
                if(DEBUG) System.out.println("Antibody credit: " + antibodyCredit);
                if(DEBUG) System.out.println("Virus left: " + Virus.amount());
                if(DEBUG) System.out.println("Antibody left: " + Antibody.amount());
                if(DEBUG) humanBody.print();
                Organism.wakeAll();
                //System.out.println(System.currentTimeMillis() - currentTime);
            }
        }
    }
}
