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
    private boolean selected;

    public Antibody(GeneticCode code) {
        super(code);
        initialHealth = Configuration.getAntibodyHealth();
        attack = Configuration.getAntibodyAttackDamage();
        killGain = Configuration.getAntibodyKillGain();
        health = initialHealth;
        antibodies.add(this);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return selected;
    }

    public boolean shoot(Direction direction) {
        Cell targetCell = cell.getNeighbor(direction);
        if (targetCell == null) return false;
        if (targetCell.isEmpty()) return false;
        Organism target = targetCell.getOrganism();
        target.receiveDamage(attack);
        if (target.isDeath()) {
            health += killGain;
            if (target instanceof Virus) {
                Game.getInstance().increaseAntibodyCredit(10);
            }
        }
        return true;
    }

    public void receiveDamage(int damage) {
        super.receiveDamage(damage);
        if (isDeath()) {
            antibodies.remove(this);
        }
    }

    public void mutate(Virus attacker) {
        Virus virus = attacker.getMutation();
        cell.setOrganism(virus);
        virus.setCell(cell);
    }

    public String toString() {
        return "Antibody";
    }
}
