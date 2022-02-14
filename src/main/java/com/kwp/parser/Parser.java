package com.kwp.parser;

import com.kwp.carin.GeneticCode;
import com.kwp.carin.Organism;
import com.kwp.util.Direction;

public class Parser {
    private final Tokenizer tokenizer;

    public Parser(String source) {
        tokenizer = new Tokenizer(source);
    }

    public Program parse() {
        Program program;
        try {
            program = parseProgram();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        if (!tokenizer.hasNext()) {
            return program;
        } else {
            System.out.println("Error: Unexpected token: " + tokenizer.consume());
            return null;
        }
    }

    private Program parseProgram() {
        Program program = new Program();
        while (tokenizer.hasNext()) {
            program.addStatement(parseStatement());
        }
        return program;
    }

    private Statement parseStatement() {
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

    private Statement parseCommand() {
        if (tokenizer.peek("move") || tokenizer.peek("shoot")) {
            return parseActionCommand();
        } else {
            return parseAssignmentStatement();
        }
    }

    private Statement parseAssignmentStatement() {
        String name = tokenizer.consume();
        try {
            tokenizer.consume("=");
        } catch (Exception e) {
            System.out.println("Error: Expected '='");
        }
        Expression expression = parseExpression();
        return new AssignmentStatement(name, expression);
    }

    private Statement parseActionCommand() {
        if (tokenizer.peek("move")) {
            return parseMoveCommand();
        } else {
            return parseAttackCommand();
        }
    }

    private Statement parseMoveCommand() {
        tokenizer.consume();
        Direction direction = Direction.valueOf(tokenizer.consume());
        return new Command("move", direction);
    }

    private Statement parseAttackCommand() {
        tokenizer.consume();
        Direction direction = Direction.valueOf(tokenizer.consume());
        return new Command("shoot", direction);
    }

    private Statement parseBlockStatement() {
        tokenizer.consume();
        BlockStatement blockStatement = new BlockStatement();
        while (!tokenizer.peek("}")) {
            blockStatement.addStatement(parseStatement());
        }
        tokenizer.consume();
        return blockStatement;
    }

    private Statement parseWhileStatement() {
        tokenizer.consume();
        try {
            tokenizer.consume("(");
        } catch (Exception e) {
            System.out.println("Error: Expected '('");
        }
        Expression condition = parseExpression();
        try {
            tokenizer.consume(")");
        } catch (Exception e) {
            System.out.println("Error: Expected ')'");
        }
        Statement statement = parseStatement();
        return new WhileStatement(condition, statement);
    }

    private Statement parseIfStatement() {
        tokenizer.consume();
        try {
            tokenizer.consume("(");
        } catch (Exception e) {
            System.out.println("Error: Expected '('");
        }
        Expression condition = parseExpression();
        try {
            tokenizer.consume(")");
        } catch (Exception e) {
            System.out.println("Error: Expected ')'");
        }
        try {
            tokenizer.consume("then");
        } catch (Exception e) {
            System.out.println("Error: Expected 'then'");
        }
        Statement thenStatement = parseStatement();
        try {
            tokenizer.consume("else");
        } catch (Exception e) {
            System.out.println("Error: Expected 'else'");
        }
        Statement elseStatement = parseStatement();
        return new IfStatement(condition, thenStatement, elseStatement);
    }

    private Expression parseExpression() {
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

    private Expression parseTerm() {
        Expression result = parseFactor();
        while (tokenizer.peek("*") || tokenizer.peek("/") || tokenizer.peek("%")) {
            String operator = tokenizer.consume();
            Expression next = parseFactor();
            if (operator.equals("*")) {
                result = new BinaryExpression(result, "*", next);
            } else if (operator.equals("/")) {
                result = new BinaryExpression(result, "/", next);
            } else {
                result = new BinaryExpression(result, "%", next);
            }
        }
        return result;
    }

    private Expression parseFactor() {
        Expression result = parsePower();
        while (tokenizer.peek("^")) {
            String operator = tokenizer.consume();
            Expression next = parsePower();
            result = new BinaryExpression(result, "^", next);
        }
        return result;
    }

    private Expression parsePower() {
        if (tokenizer.peek("virus") || tokenizer.peek("antibody") || tokenizer.peek("nearby")) {
            return parseSensorExpression();
        } else if (tokenizer.peek("(")) {
            tokenizer.consume();
            Expression result = parseExpression();
            try {
                tokenizer.consume(")");
            } catch (Exception e) {
                System.out.println("Error: Expected ')'");
            }
            return result;
        } else if (Character.isLetter(tokenizer.peek().charAt(0))) {
            return new Identifier(tokenizer.consume());
        } else if (Character.isDigit(tokenizer.peek().charAt(0))) {
            return new Number(Integer.parseInt(tokenizer.consume()));
        } else {
            throw new RuntimeException("Error: Expected a number or a variable");
        }
    }

    private Expression parseSensorExpression() {
        if (tokenizer.peek("virus")) {
            return new SensorExpression(tokenizer.consume());
        } else if (tokenizer.peek("antibody")) {
            return new SensorExpression(tokenizer.consume());
        } else {
            return new SensorExpression(tokenizer.consume(), Direction.valueOf(tokenizer.consume()));
        }
    }

    public static void main(String[] args) {
        GeneticCode code = GeneticCode.getTest0();
        Parser parser = new Parser(code.getCode());
        Program program = parser.parse();
        System.out.println(program);
    }
}
