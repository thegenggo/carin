package com.kwp.parser;

import com.kwp.carin.Organism;
import com.kwp.util.CarinRandom;

import java.util.Map;

public class Identifier implements Expression {
    private String name;

    public Identifier(String name) {
        this.name = name;
    }

    public int eval(Map<String, Integer> variables, Organism organism) {
        if (name.equals("random")) return CarinRandom.nextInt(100);
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else {
            return variables.put(name, 0) == null ? 0 : variables.get(name);
        }
    }
}
