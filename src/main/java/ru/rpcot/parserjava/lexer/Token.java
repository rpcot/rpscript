package ru.rpcot.parserjava.lexer;

public class Token {
    private final TokenType type;
    private final String value;
    private final int line;

    Token(TokenType type, int line, String value) {
        this.type = type;
        this.line = line;
        this.value = value;
    }

    Token(TokenType type, int line) {
        this.type = type;
        this.line = line;
        this.value = null;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return String.format("Token<%s, %s>", getType(), getValue());
    }
}