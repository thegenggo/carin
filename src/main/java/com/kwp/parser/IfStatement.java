package com.kwp.parser;

import com.kwp.carin.Organism;

import java.util.Map;

public class IfStatement implements Statement {
    private final Expression condition;
    private final Statement thenStatement;
    private final Statement elseStatement;

    public IfStatement(Expression condition, Statement thenStatement, Statement elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public void execute(Map<String, Integer> variables, Organism organisms) {
        if (condition.eval(variables, organisms) > 0) {
            thenStatement.execute(variables, organisms);
        } else {
            elseStatement.execute(variables, organisms);
        }
    }
}
