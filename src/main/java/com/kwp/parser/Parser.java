package com.kwp.parser;

import com.kwp.util.Direction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Parser {
    private final Tokenizer tokenizer;
    private final GeneticCode code;
    private static final Set<String> reservedWords = new HashSet<>();

    private Parser(GeneticCode code) {
        this.tokenizer = new Tokenizer(code.getCode());
        this.code = code;
        if (reservedWords.isEmpty()) {
            String[] reservedWordsArrays = {"antibody", "down", "downleft", "downright", "else", "if",
                    "left", "move", "nearby", "right", "shoot", "then", "up", "upleft", "upright", "virus", "while"};
            Collections.addAll(reservedWords, reservedWordsArrays);
        }
    }

    public static Program parse(GeneticCode code) throws SyntaxError {
        if (Program.contain(code)) {
            return Program.getInstance(code);
        }
        try {
            return new Parser(code).parseProgram();
        } catch (SyntaxError e) {
            Program.remove(code);
            throw e;
        }
    }

    private Program parseProgram() throws SyntaxError {
        Program program = Program.getInstance(code);
        while (tokenizer.hasNext()) {
            program.addStatement(parseStatement());
        }
        return program;
    }

    private Statement parseStatement() throws SyntaxError {
        if (tokenizer.peek("if")) {
            return parseIfStatement();
        } else if (tokenizer.peek("while")) {
            return parseWhileStatement();
        } else if (tokenizer.peek("{")) {
            return parseBlockStatement();
        } else {
            return parseCommand();
        }
    }

    private Statement parseCommand() throws SyntaxError {
        if (tokenizer.peek("move") || tokenizer.peek("shoot")) {
            return parseActionCommand();
        } else {
            return parseAssignmentStatement();
        }
    }

    private Statement parseAssignmentStatement() throws SyntaxError {
        String name = tokenizer.consume();
        tokenizer.consume("=");
        Expression expression = parseExpression();
        return new AssignmentStatement(name, expression);
    }

    private Statement parseActionCommand() throws SyntaxError {
        if (tokenizer.peek("move")) {
            return parseMoveCommand();
        } else {
            return parseAttackCommand();
        }
    }

    private Statement parseMoveCommand() throws SyntaxError {
        tokenizer.consume();
        try {
            Direction direction = Direction.valueOf(tokenizer.consume());
            return new Command("move", direction);
        } catch (Exception e) {
            throw new SyntaxError("Expected: direction at line " + tokenizer.getLineNumber());
        }
    }

    private Statement parseAttackCommand() throws SyntaxError {
        tokenizer.consume();
        try {
            Direction direction = Direction.valueOf(tokenizer.consume());
            return new Command("shoot", direction);
        } catch (Exception e) {
            throw new SyntaxError("Expected: direction at line " + tokenizer.getLineNumber());
        }
    }

    private Statement parseBlockStatement() throws SyntaxError {
        tokenizer.consume();
        BlockStatement blockStatement = new BlockStatement();
        while (!tokenizer.peek("}")) {
            blockStatement.addStatement(parseStatement());
        }
        tokenizer.consume();
        return blockStatement;
    }

    private Statement parseWhileStatement() throws SyntaxError {
        tokenizer.consume();
        tokenizer.consume("(");
        Expression condition = parseExpression();
        tokenizer.consume(")");
        Statement statement = parseStatement();
        return new WhileStatement(condition, statement);
    }

    private Statement parseIfStatement() throws SyntaxError {
        tokenizer.consume();
        tokenizer.consume("(");
        Expression condition = parseExpression();
        tokenizer.consume(")");
        tokenizer.consume("then");
        Statement thenStatement = parseStatement();
        tokenizer.consume("else");
        Statement elseStatement = parseStatement();
        return new IfStatement(condition, thenStatement, elseStatement);
    }

    private Expression parseExpression() throws SyntaxError {
        Expression result = parseTerm();
        while (tokenizer.peek("+") || tokenizer.peek("-")) {
            String operator = tokenizer.consume();
            Expression next = parseTerm();
            if (operator.equals("+")) {
                result = new BinaryExpression(result, "+", next);
            } else {
                result = new BinaryExpression(result, "-", next);
            }
        }
        return result;
    }

    private Expression parseTerm() throws SyntaxError {
        Expression result = parseFactor();
        while (tokenizer.peek("*") || tokenizer.peek("/") || tokenizer.peek("%")) {
            String operator = tokenizer.consume();
            Expression next = parseFactor();
            result = new BinaryExpression(result, operator, next);
        }
        return result;
    }

    private Expression parseFactor() throws SyntaxError {
        Expression result = parsePower();
        while (tokenizer.peek("^")) {
            String operator = tokenizer.consume();
            Expression next = parseFactor();
            result = new BinaryExpression(result, operator, next);
        }
        return result;
    }

    private Expression parsePower() throws SyntaxError {
        if (tokenizer.peek("virus") || tokenizer.peek("antibody") || tokenizer.peek("nearby")) {
            return parseSensorExpression();
        } else if (tokenizer.peek("(")) {
            tokenizer.consume();
            Expression result = parseExpression();
            tokenizer.consume(")");
            return result;
        } else if (Character.isLetter(tokenizer.peek().charAt(0))) {
            if (Parser.reservedWords.contains(tokenizer.peek()))
                throw new SyntaxError("Error: " + tokenizer.peek() + " is a reserved word");
            return new Identifier(tokenizer.consume());
        } else if (Character.isDigit(tokenizer.peek().charAt(0))) {
            return new Number(Integer.parseInt(tokenizer.consume()));
        } else {
            throw new SyntaxError("Expected: a number or a variable at line " + tokenizer.getLineNumber());
        }
    }

    private Expression parseSensorExpression() throws SyntaxError {
        if (tokenizer.peek("virus")) {
            return new SensorExpression(tokenizer.consume());
        } else if (tokenizer.peek("antibody")) {
            return new SensorExpression(tokenizer.consume());
        } else {
            try {
                return new SensorExpression(tokenizer.consume(), Direction.valueOf(tokenizer.consume()));
            } catch (Exception e) {
                throw new SyntaxError("Expected: direction at line " + tokenizer.getLineNumber());
            }
        }
    }
}
