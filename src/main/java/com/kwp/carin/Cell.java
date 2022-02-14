package com.kwp.carin;

import com.kwp.util.Pair;

public class Cell {
    private final Pair<Integer, Integer> position;
    private Organism organism;

    public Cell(int i, int j) {
        position = new Pair<>(i, j);
    }

    public boolean isEmpty() {
        return organism == null;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    public void clear() {
        organism = null;
    }

    public Organism getOrganism() {
        return organism;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }
}
