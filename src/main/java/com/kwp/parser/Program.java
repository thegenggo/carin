package com.kwp.parser;

import com.kwp.carin.GeneticCode;
import com.kwp.carin.Organism;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Program {
    LinkedList<Statement> statements;
    Map<String, Integer> variables;

    public Program() {
        statements = new LinkedList<>();
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    public void evaluate(Organism organism) {
        for (Statement statement : statements) {
            statement.execute(variables, organism);
        }
    }

    public static Map<GeneticCode, Program> instances;

    public static Program getInstance(GeneticCode code) {
        if (instances == null) {
            instances = new HashMap<>();
        }
        if (!instances.containsKey(code)) {
            Parser parser = new Parser(code.getCode());
            instances.put(code, new Program());
        }
        return instances.get(code);
    }
}
