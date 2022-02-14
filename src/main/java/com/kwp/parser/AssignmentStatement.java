package com.kwp.parser;

import com.kwp.carin.Organism;

import java.util.Map;

public class AssignmentStatement implements Statement {
    private final String variable;
    private final Expression expression;

    public AssignmentStatement(String name, Expression expression) {
        this.variable = name;
        this.expression = expression;
    }

    public void execute(Map<String, Integer> variables, Organism organism) {
        variables.put(variable, expression.eval(variables, organism));
    }
}
