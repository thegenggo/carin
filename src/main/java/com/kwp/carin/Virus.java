package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.util.CarinRandom;
import com.kwp.util.Direction;

import java.util.LinkedList;

public abstract class Virus extends Organism {
    private static final LinkedList<Virus> viruses = new LinkedList<>();
    private static int spawned = 0;

    public static int amount() {
        return viruses.size();
    }

    public static void reset() {
        viruses.clear();
        spawned = 0;
    }

    public static int getSpawned() {
        return spawned;
    }

    private final int attackGain;

    protected Virus(GeneticCode code) {
        super(code);
        initialHealth = Configuration.getVirusHealth();
        attack = Configuration.getVirusAttackDamage();
        attackGain = Configuration.getVirusAttackGain();
        health = initialHealth;
        viruses.add(this);
        spawned++;
    }

    public boolean shoot(Direction direction) {
        if (!isReady()) return false;
        Cell targetCell = cell.getNeighbor(direction);
        if (targetCell == null) return false;
        if (targetCell.isEmpty()) return false;
        Organism target = targetCell.getOrganism();
        target.receiveDamage(attack);
        health += attackGain;
        if (target.isDeath() && !target.isVirus()) {
            Virus mutation = getMutation();
            targetCell.setOrganism(mutation);
            mutation.setCell(targetCell);
        }
        return true;
    }

    public void receiveDamage(int damage) {
        super.receiveDamage(damage);
        if (isDeath()) {
            viruses.remove(this);
        }
    }

    public String toString() {
        return "Virus";
    }

    protected abstract Virus getMutation();

    public static Virus getRandomVirus() {
        return switch (CarinRandom.nextInt(3)) {
            case 0 -> new Alpha();
            case 1 -> new Beta();
            case 2 -> new Gamma();
            default -> throw new IllegalStateException("Unexpected value: " + CarinRandom.nextInt(3));
        };
    }
}
