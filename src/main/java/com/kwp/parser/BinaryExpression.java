package com.kwp.parser;

import com.kwp.carin.Organism;

import java.util.Map;

public class BinaryExpression implements Expression {
    private Expression left;
    private Expression right;
    private String operator;

    public BinaryExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public int eval(Map<String, Integer> variables, Organism organism) {
        return 0;
    }
}
