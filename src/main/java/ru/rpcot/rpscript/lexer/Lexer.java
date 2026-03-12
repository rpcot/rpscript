package ru.rpcot.rpscript.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int currentIndex = 0;
    private int line = 1;

    public Lexer(String input) {
        this.input = input;
    }

    private char getChar() {
        if (this.currentIndex >= input.length()) {
            return '\0';
        }
        return this.input.charAt(this.currentIndex);
    }

    private char getNextChar() {
        if (this.currentIndex + 1 >= input.length()) {
            return '\0';
        }
        return this.input.charAt(this.currentIndex + 1);
    }

    public List<Token> tokenize() throws RuntimeException {
        List<Token> tokens = new ArrayList<>();

        while (this.currentIndex < this.input.length()) {
            char ch = this.input.charAt(this.currentIndex);

            switch (ch) {
                case '\n' -> {
                    line++;
                    this.currentIndex++;
                }
                case '\t', '\r', '\s' -> this.currentIndex++;
                case '+' -> {
                    tokens.add(new Token(TokenType.PLUS, line));
                    this.currentIndex++;
                }
                case '=' -> {
                    if (getNextChar() == '=') {
                        tokens.add(new Token(TokenType.EQUALS, line));
                        this.currentIndex++;
                    } else tokens.add(new Token(TokenType.ASSIGN, line));
                    this.currentIndex++;
                }
                case '!' -> {
                    if (getNextChar() == '=') {
                        tokens.add(new Token(TokenType.NOT_EQUALS, line));
                        this.currentIndex++;
                    } else throw new RuntimeException(String.format("unexpected character %s", ch));
                    this.currentIndex++;
                }
                case '-' -> {
                    tokens.add(new Token(TokenType.MINUS, line));
                    this.currentIndex++;
                }
                case '*' -> {
                    if (getNextChar() == '*') {
                        tokens.add(new Token(TokenType.POW, line));
                        this.currentIndex++;
                    } else tokens.add(new Token(TokenType.MUL, line));
                    this.currentIndex++;
                }
                case '>' -> {
                    if (getNextChar() == '=') {
                        tokens.add(new Token(TokenType.GREATER_EQUALS, line));
                        this.currentIndex++;
                    } else tokens.add(new Token(TokenType.GREATER, line));
                    this.currentIndex++;
                }
                case '<' -> {
                    if (getNextChar() == '=') {
                        tokens.add(new Token(TokenType.LESS_EQUALS, line));
                        this.currentIndex++;
                    } else tokens.add(new Token(TokenType.LESS, line));
                    this.currentIndex++;
                }
                case '|' -> {
                    if (getNextChar() == '|') {
                        tokens.add(new Token(TokenType.OR, line));
                        this.currentIndex++;
                    } else throw new RuntimeException(String.format("unexpected character %s", ch));
                    this.currentIndex++;
                }
                case '&' -> {
                    if (getNextChar() == '&') {
                        tokens.add(new Token(TokenType.AND, line));
                        this.currentIndex++;
                    } else throw new RuntimeException(String.format("unexpected character %s", ch));
                    this.currentIndex++;
                }
                case '/' -> {
                    tokens.add(new Token(TokenType.DIV, line));
                    this.currentIndex++;
                }
                case ',' -> {
                    tokens.add(new Token(TokenType.COMMA, line));
                    this.currentIndex++;
                }
                case ';' -> {
                    tokens.add(new Token(TokenType.SEMICOLON, line));
                    this.currentIndex++;
                }
                case '(' -> {
                    tokens.add(new Token(TokenType.LPAR, line));
                    this.currentIndex++;
                }
                case ')' -> {
                    tokens.add(new Token(TokenType.RPAR, line));
                    this.currentIndex++;
                }
                case '{' -> {
                    tokens.add(new Token(TokenType.LCURLY, line));
                    this.currentIndex++;
                }
                case '}' -> {
                    tokens.add(new Token(TokenType.RCURLY, line));
                    this.currentIndex++;
                }
                case '\'' -> {
                    StringBuilder buffer = new StringBuilder();
                    this.currentIndex++;
                    while (this.currentIndex < this.input.length() && getChar() != '\'') {
                        char current = getChar();
                        if (current == '\\') {
                            this.currentIndex++;
                            char next = getChar();
                            if (next == 'n') buffer.append('\n');
                            else if (next == 't') buffer.append('\t');
                            else if (next == '\'') buffer.append('\'');
                            else if (next == '\\') buffer.append('\\');
                        } else {
                            buffer.append(current);
                        }
                        this.currentIndex++;
                    }
                    this.currentIndex++;
                    String string = buffer.toString();
                    tokens.add(new Token(TokenType.STRING, line, string));
                }

                default -> {
                    if (Character.isAlphabetic(ch)) {
                        StringBuilder buffer = new StringBuilder();
                        while (Character.isLetterOrDigit(ch) || ch == '_') {
                            buffer.append(ch);
                            this.currentIndex++;
                            ch = getChar();
                        }
                        String value = buffer.toString();
                        TokenType type = switch (value) {
                            case "fun" -> TokenType.FUN;
                            case "var" -> TokenType.VAR;
                            case "const" -> TokenType.CONST;
                            case "return" -> TokenType.RETURN;
                            case "void" -> TokenType.VOID;
                            case "if" -> TokenType.IF;
                            case "else" -> TokenType.ELSE;
                            case "true", "false" -> TokenType.BOOLEAN;
                            case "for" -> TokenType.FOR;
                            case "break" -> TokenType.BREAK;
                            case "continue" -> TokenType.CONTINUE;
                            case "null" -> TokenType.NULL;
                            default -> TokenType.IDENTIFIER;
                        };
                        tokens.add(new Token(type, line, value));
                    } else if (Character.isDigit(getChar())) {
                        StringBuilder buffer = new StringBuilder();
                        while (Character.isDigit(getChar()) || getChar() == '.') {
                            buffer.append(getChar());
                            this.currentIndex++;
                        }
                        String value = buffer.toString();
                        tokens.add(new Token(TokenType.NUMBER, line, value));
                    } else throw new RuntimeException(String.format("unexpected character: %s", ch));
                }
            }
        }
        tokens.add(new Token(TokenType.EOF, line));
        return tokens;
    }
}
