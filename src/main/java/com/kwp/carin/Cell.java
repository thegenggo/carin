package com.kwp.carin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kwp.util.Direction;
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

    @JsonIgnore
    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public Cell getNeighbor(Direction direction) {
        HumanBody humanBody = HumanBody.getInstance();
        return switch (direction) {
            case up -> humanBody.getCell(position.fst() - 1, position.snd());
            case down -> humanBody.getCell(position.fst() + 1, position.snd());
            case left -> humanBody.getCell(position.fst(), position.snd() - 1);
            case right -> humanBody.getCell(position.fst(), position.snd() + 1);
            case upleft -> humanBody.getCell(position.fst() - 1, position.snd() - 1);
            case upright -> humanBody.getCell(position.fst() - 1, position.snd() + 1);
            case downleft -> humanBody.getCell(position.fst() + 1, position.snd() - 1);
            case downright -> humanBody.getCell(position.fst() + 1, position.snd() + 1);
        };
    }

    public String toString() {
        return String.format("[%-10s:%3s]", organism == null ? " " : organism, organism == null ? " " : organism.getHealth());
    }
}
