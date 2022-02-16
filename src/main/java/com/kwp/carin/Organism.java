package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;
import com.kwp.parser.Program;
import com.kwp.util.Direction;
import com.kwp.util.Pair;

import java.util.LinkedList;

public abstract class Organism {
    private static final LinkedList<Organism> organisms = new LinkedList<>();
    protected static GeneticCode geneticCode = null;

    public static boolean setGeneticCode(GeneticCode code) {
        Parser parser = new Parser(code);
        if (parser.parse() == null) return false;
        geneticCode = code;
        return true;
    }

    protected Program program;
    protected Cell cell;
    protected int initialHealth;
    protected int health;
    protected int attack;
    protected boolean ready;

    public Organism(GeneticCode code) {
        program = Program.getInstance(code);
        organisms.add(this);
        ready = true;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public boolean isAntibody() {
        return this instanceof Antibody;
    }

    public boolean isVirus() {
        return this instanceof Virus;
    }

    protected boolean isDeath() {
        return health <= 0;
    }

    public boolean isReady() {
        return ready;
    }

    public void move(Direction direction) {
        if (!ready) return;
        Cell target = cell.getNeighbor(direction);
        if (target != null && target.isEmpty()) {
            cell.clear();
            target.setOrganism(this);
            cell = target;
        }
        ready = false;
    }

    /** @return true if target is died */
    public boolean shoot(Direction direction) {
        Cell targetCell = cell.getNeighbor(direction);
        if (targetCell == null) return false;
        Organism target = targetCell.getOrganism();
        if (target != null) {
            target.receiveDamage(attack);
            return target.isDeath();
        }
        ready = false;
        return false;
    }

    protected void receiveDamage(int damage) {
        health -= damage;
        if (isDeath()) {
            cell.clear();
            organisms.remove(this);
        }
    }

    public int antibodySensor() {
        Pair<Integer, Integer> position = cell.getPosition();
        int i = position.fst(), j = position.snd();
        for (int k = 1; k <= 3; k++) {
            if (checkAntiBody(i - k, j)) return k * 10 + 1;
            if (checkAntiBody(i - k, j + k)) return k * 10 + 2;
            if (checkAntiBody(i, j + k)) return k * 10 + 3;
            if (checkAntiBody(i + k, j + k)) return k * 10 + 4;
            if (checkAntiBody(i + k, j)) return k * 10 + 5;
            if (checkAntiBody(i + k, j - k)) return k * 10 + 6;
            if (checkAntiBody(i, j - k)) return k * 10 + 7;
            if (checkAntiBody(i - k, j - k)) return k * 10 + 8;
        }
        return 0;
    }

    private boolean checkAntiBody(int i, int j) {
        HumanBody body = HumanBody.getInstance();
        Cell target = body.getCell(i, j);
        return target != null && !target.isEmpty() && target.getOrganism().isAntibody();
    }

    public int virusSensor() {
        Pair<Integer, Integer> position = cell.getPosition();
        int i = position.fst(), j = position.snd();
        for (int k = 1; k <= 3; k++) {
            if (checkVirus(i - k, j)) return k * 10 + 1;
            if (checkVirus(i - k, j + k)) return k * 10 + 2;
            if (checkVirus(i, j + k)) return k * 10 + 3;
            if (checkVirus(i + k, j + k)) return k * 10 + 4;
            if (checkVirus(i + k, j)) return k * 10 + 5;
            if (checkVirus(i + k, j - k)) return k * 10 + 6;
            if (checkVirus(i, j - k)) return k * 10 + 7;
            if (checkVirus(i - k, j - k)) return k * 10 + 8;
        }
        return 0;
    }

    private boolean checkVirus(int i, int j) {
        HumanBody body = HumanBody.getInstance();
        Cell target = body.getCell(i, j);
        return target != null && !target.isEmpty() && target.getOrganism().isVirus();
    }

    public int nearbySensor(Direction direction) {
        Pair<Integer, Integer> position = cell.getPosition();
        int i = position.fst(), j = position.snd();
        switch (direction) {
            case up: for (int k = 1; k <= 3; k++) {
                if (checkNearby(i - k, j)) return k * 10 + 1;
            }
            break;
            case upright: for (int k = 1; k <= 3; k++) {
                if (checkNearby(i - k, j + k)) return k * 10 + 2;
            }
            break;
            case right: for (int k = 1; k <= 3; k++) {
                if (checkNearby(i, j + k)) return k * 10 + 3;
            }
            break;
            case downright: for (int k = 1; k <= 3; k++) {
                if (checkNearby(i + k, j + k)) return k * 10 + 4;
            }
            break;
            case down: for (int k = 1; k <= 3; k++) {
                if (checkNearby(i + k, j)) return k * 10 + 5;
            }
            break;
            case downleft: for (int k = 1; k <= 3; k++) {
                if (checkNearby(i + k, j - k)) return k * 10 + 6;
            }
            break;
            case left: for (int k = 1; k <= 3; k++) {
                if (checkNearby(i, j - k)) return k * 10 + 7;
            }
            break;
            case upleft: for (int k = 1; k <= 3; k++) {
                if (checkNearby(i - k, j - k)) return k * 10 + 8;
            }
            break;
        }
        return 0;
    }

    private boolean checkNearby(int i, int j) {
        HumanBody body = HumanBody.getInstance();
        Cell target = body.getCell(i, j);
        return target != null && !target.isEmpty();
    }

    public void evaluate() {
        program.evaluate(this);
    }

    public static void runAll() {
        LinkedList<Organism> copy = new LinkedList<>(organisms);
        for (Organism organism : copy) {
            if (!organism.isDeath()) organism.evaluate();
        }
    }

    public static void wakeAll() {
        LinkedList<Organism> copy = new LinkedList<>(organisms);
        for (Organism organism : copy) {
            organism.ready = true;
        }
    }
}
