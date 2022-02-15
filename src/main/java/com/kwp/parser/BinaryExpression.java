package com.kwp.parser;

import com.kwp.carin.Organism;

import java.util.Map;

public class BinaryExpression implements Expression {
    private final Expression left;
    private final Expression right;
    private final String operator;

    public BinaryExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public int eval(Map<String, Integer> variables, Organism organism) {
        return switch (operator) {
            case "+" -> left.eval(variables, organism) + right.eval(variables, organism);
            case "-" -> left.eval(variables, organism) - right.eval(variables, organism);
            case "*" -> left.eval(variables, organism) * right.eval(variables, organism);
            case "/" -> left.eval(variables, organism) / right.eval(variables, organism);
            case "%" -> left.eval(variables, organism) % right.eval(variables, organism);
            case "^" -> (int) Math.pow(left.eval(variables, organism), right.eval(variables, organism));
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}
