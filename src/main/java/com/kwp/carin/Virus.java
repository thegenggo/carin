package com.kwp.carin;

import com.kwp.util.Direction;

import java.util.LinkedList;

public class Virus extends Organism {
    private static final LinkedList<Virus> viruses = new LinkedList<>();

    public static int amount() {
        return viruses.size();
    }

    private final int attackGain;

    public Virus(GeneticCode code) {
        super(code);
        initialHealth = Configuration.getVirusHealth();
        attack = Configuration.getVirusAttackDamage();
        attackGain = Configuration.getVirusAttackGain();
        health = initialHealth;
        viruses.add(this);
    }

    public boolean shoot(Direction direction) {
        if (!ready) return false;
        boolean isTargetDied = super.shoot(direction);
        health += attackGain;
        ready = false;
        return isTargetDied;
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
}
