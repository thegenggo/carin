package com.kwp.carin;

import com.kwp.util.Direction;

public abstract class Organism {
    GeneticCode code;
    int maxHealth;
    int health;
    int attack;

    public Organism(GeneticCode code) {
        this.code = code;
    }

    public void move(Direction direction) {

    }

    public void shoot(Direction direction) {

    }

    public int antibodySensor() {
        return 0;
    }

    public int virusSensor() {
        return 0;
    }

    public int nearbySensor(Direction direction) {
        return 0;
    }
}
