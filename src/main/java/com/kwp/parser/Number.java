package com.kwp.parser;

import com.kwp.carin.Organism;

import java.util.Map;

public class Number implements Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    public int eval(Map<String, Integer> variables, Organism organism) {
        return value;
    }
}
