package com.kwp.parser;

import com.kwp.carin.Organism;

import java.util.Map;

public interface Statement {
    void execute(Map<String, Integer> variables, Organism organism);
}
