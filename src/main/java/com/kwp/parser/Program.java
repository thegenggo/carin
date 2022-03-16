package com.kwp.parser;

import com.kwp.carin.Organism;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Program {
    public static Map<GeneticCode, Program> instances = new HashMap<>();

    public static Program getInstance(GeneticCode code) {
        if (!instances.containsKey(code)) {
            Program program = new Program();
            instances.put(code, program);
        }
        return instances.get(code);
    }

    LinkedList<Statement> statements;

    private Program() {
        statements = new LinkedList<>();
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    public void evaluate(Organism organism, Map<String, Integer> variables) {
        for (Statement statement : statements) {
            statement.execute(variables, organism);
        }
    }

    public static boolean contain(GeneticCode code) {
        return instances.containsKey(code);
    }

    public static void remove(GeneticCode code) {
        instances.remove(code);
    }
}
