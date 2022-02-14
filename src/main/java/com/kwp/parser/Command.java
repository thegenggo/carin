package com.kwp.parser;

import com.kwp.carin.Organism;
import com.kwp.util.Direction;

import java.util.Map;

public class Command implements Statement {
    protected final String command;
    protected final Direction direction;

    public Command(String command, Direction direction) {
        this.command = command;
        this.direction = direction;
    }

    public void execute(Map<String, Integer> variables, Organism organism) {
        if (command.equals("move")) {
            organism.move(direction);
        } else if (command.equals("shoot")) {
            organism.shoot(direction);
        } else {
            throw new IllegalArgumentException("Unknown command: " + command);
        }
    }
}
