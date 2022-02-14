package com.kwp.parser;

import com.kwp.carin.Organism;
import com.kwp.util.Direction;

import java.util.Map;

public class SensorExpression extends Command implements Expression {
    public SensorExpression(String command, Direction direction) {
        super(command, direction);
    }

    public SensorExpression(String command) {
        super(command, null);
    }

    public int eval(Map<String, Integer> variables, Organism organism) {
        if (command.equals("virus")) {
            return organism.virusSensor();
        } else if (command.equals("antibody")) {
            return organism.antibodySensor();
        } else {
            return organism.nearbySensor(direction);
        }
    }
}
