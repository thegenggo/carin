package com.kwp.carin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kwp.parser.GeneticCode;
import com.kwp.parser.Program;
import com.kwp.util.Direction;
import com.kwp.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class Organism {
    private static final LinkedList<Organism> organisms = new LinkedList<>();

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

    public static void reset() {
        organisms.clear();
        Antibody.reset();
        Virus.reset();
    }

    protected Program program;
    protected Map<String, Integer> variables;
    protected Cell cell;
    protected int initialHealth;
    protected int health;
    protected int attack;

    protected boolean ready;

    public Organism(GeneticCode code) {
        program = Program.getInstance(code);
        organisms.add(this);
        variables = new HashMap<>();
        ready = false;
    }

    public int getHealth() {
        return health;
    }

    @JsonIgnore
    public Cell getCell() {
        return cell;
    }

    public int getAttack() {
        return attack;
    }

    public boolean getSelected() {
        return false;
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

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
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

    /** @return true if attack success */
    public abstract boolean shoot(Direction direction);

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
        program.evaluate(this, variables);
    }

    public String getType() {
        return toString();
    }
}
