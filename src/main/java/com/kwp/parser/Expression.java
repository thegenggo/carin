package com.kwp.parser;

import com.kwp.carin.Organism;

import java.util.Map;

public interface Expression {
    int eval(Map<String, Integer> variables, Organism organism);
}
