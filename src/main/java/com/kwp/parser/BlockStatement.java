package com.kwp.parser;

import com.kwp.carin.Organism;

import java.util.LinkedList;
import java.util.Map;

public class BlockStatement implements Statement {
    private final LinkedList<Statement> statements;

    public BlockStatement() {
        this.statements = new LinkedList<>();
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    public void execute(Map<String, Integer> variables, Organism organism) {
        for (Statement statement : statements) {
            statement.execute(variables, organism);
        }
    }
}
