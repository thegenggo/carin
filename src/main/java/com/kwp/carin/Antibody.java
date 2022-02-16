package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.util.Direction;

import java.util.LinkedList;

public class Antibody extends Organism {
    private static final LinkedList<Antibody> antibodies = new LinkedList<>();

    public static int amount() {
        return antibodies.size();
    }

    private final int killGain;

    public Antibody(GeneticCode code) {
        super(code);
        initialHealth = Configuration.getAntibodyHealth();
        attack = Configuration.getAntibodyAttackDamage();
        killGain = Configuration.getAntibodyKillGain();
        health = initialHealth;
        antibodies.add(this);
    }

    public boolean shoot(Direction direction) {
        if (!ready) return false;
        boolean isTargetDied = super.shoot(direction);
        if (isTargetDied) health += killGain;
        ready = false;
        return isTargetDied;
    }

    public void receiveDamage(int damage) {
        super.receiveDamage(damage);
        if (isDeath()) {
            antibodies.remove(this);
        }
    }

    public String toString() {
        return "Antibody";
    }
}
