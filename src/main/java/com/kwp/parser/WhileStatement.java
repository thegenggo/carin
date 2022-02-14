package com.kwp.parser;

import com.kwp.carin.Organism;

import java.util.Map;

public class WhileStatement implements Statement {
    private final Expression condition;
    private final Statement body;

    public WhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    public void execute(Map<String, Integer> variables, Organism organism) {
        for (int counter = 0; counter < 1000 && condition.eval(variables, organism) > 0; counter++) {
            body.execute(variables, organism);
        }
    }
}
