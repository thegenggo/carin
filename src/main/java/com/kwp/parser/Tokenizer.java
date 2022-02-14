package com.kwp.parser;

public class Tokenizer {
    private String source;
    private String next;
    private int position;

    public Tokenizer(String source) {
        this.source = source;
        computeNext();
    }

    public String peek() {
        return next;
    }

    public boolean peek(String s) {
        return s.equals(next);
    }

    public String consume() {
        String result = next;
        computeNext();
        return result;
    }

    public String consume(String expected) throws SyntaxError {
        if (!expected.equals(next)) throw new SyntaxError("Expected " + expected + " but found " + next);
        return consume();
    }

    public boolean hasNext() {
        return next != null;
    }

    private void computeNext() {
        while (position < source.length() && Character.isWhitespace(source.charAt(position))) position++;
        if (position >= source.length()) { next = null; return; }
        StringBuilder stringBuilder = new StringBuilder();
        char character = source.charAt(position);
        if (isOperator(character) || isPunctuation(character)) {
            stringBuilder.append(character);
            position++;
        } else if (Character.isLetter(character)) {
            while (position < source.length() && (Character.isLetter(source.charAt(position)) || Character.isDigit(source.charAt(position)))) {
                stringBuilder.append(source.charAt(position));
                position++;
            }
        } else if (Character.isDigit(character)) {
            while (position < source.length() && Character.isDigit(source.charAt(position))) {
                stringBuilder.append(source.charAt(position));
                position++;
            }
        } else throw new RuntimeException("Unexpected character: " + character);
        next = stringBuilder.toString();
    }

    private boolean isOperator(char character) {
        return character == '+' || character == '-' || character == '*' || character == '/' || character == '%' || character == '=';
    }

    private boolean isPunctuation(char character) {
        return character == '(' || character == ')' || character == '{' || character == '}';
    }
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer("t = t + 1\n" +
                "virusLoc = virus\n" +
                "if (virusLoc / 10 - 1)\n" +
                "then\n" +
                "  if (virusLoc % 10 - 7) then move upleft\n" +
                "  else if (virusLoc % 10 - 6) then move left\n" +
                "  else if (virusLoc % 10 - 5) then move downleft\n" +
                "  else if (virusLoc % 10 - 4) then move down\n" +
                "  else if (virusLoc % 10 - 3) then move downright\n" +
                "  else if (virusLoc % 10 - 2) then move right\n" +
                "  else if (virusLoc % 10 - 1) then move upright\n" +
                "  else move up\n" +
                "else if (virusLoc)\n" +
                "then\n" +
                "  if (virusLoc % 10 - 7) then shoot upleft\n" +
                "  else if (virusLoc % 10 - 6) then shoot left\n" +
                "  else if (virusLoc % 10 - 5) then shoot downleft\n" +
                "  else if (virusLoc % 10 - 4) then shoot down\n" +
                "  else if (virusLoc % 10 - 3) then shoot downright\n" +
                "  else if (virusLoc % 10 - 2) then shoot right\n" +
                "  else if (virusLoc % 10 - 1) then shoot upright\n" +
                "  else shoot up\n" +
                "else\n" +
                "{\n" +
                "  dir = random % 8\n" +
                "  if (dir - 6) then move upleft\n" +
                "  else if (dir - 5) then move left\n" +
                "  else if (dir - 4) then move downleft\n" +
                "  else if (dir - 3) then move down\n" +
                "  else if (dir - 2) then move downright\n" +
                "  else if (dir - 1) then move right\n" +
                "  else if (dir) then move upright\n" +
                "  else move up\n" +
                "}\n");
        while (tokenizer.hasNext()) System.out.println(tokenizer.consume());
    }
}
