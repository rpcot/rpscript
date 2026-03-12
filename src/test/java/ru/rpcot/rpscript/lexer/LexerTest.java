package ru.rpcot.rpscript.lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    @Test
    void tokenizeAssignPlus() {
        String input = "a = 2 + 2";

        Lexer lexer = new Lexer(input);

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("a", tokens.get(0).getValue());
        assertEquals(TokenType.ASSIGN, tokens.get(1).getType());
        assertEquals(TokenType.NUMBER, tokens.get(2).getType());
        assertEquals("2", tokens.get(2).getValue());
        assertEquals(TokenType.PLUS, tokens.get(3).getType());
        assertEquals(TokenType.NUMBER, tokens.get(4).getType());
        assertEquals("2", tokens.get(4).getValue());
    }

    @Test
    void tokenizeFloat() {
        String input = "2.45";

        Lexer lexer = new Lexer(input);

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
        assertEquals("2.45", tokens.get(0).getValue());
    }

    @Test
    void tokenizeHugeMath() {
        String input = "(2 + 2 ** 2) - 452 / 2.5";

        Lexer lexer = new Lexer(input);

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.LPAR, tokens.get(0).getType());
        assertEquals("2", tokens.get(1).getValue());
        assertEquals(TokenType.PLUS, tokens.get(2).getType());
        assertEquals("2", tokens.get(3).getValue());
        assertEquals(TokenType.POW, tokens.get(4).getType());
        assertEquals("2", tokens.get(5).getValue());
        assertEquals(TokenType.RPAR, tokens.get(6).getType());
        assertEquals(TokenType.MINUS, tokens.get(7).getType());
        assertEquals("452", tokens.get(8).getValue());
        assertEquals(TokenType.DIV, tokens.get(9).getType());
        assertEquals("2.5", tokens.get(10).getValue());
    }

    @Test
    void tokenizeFunDecalration() {
        String input = "fun test() {}";

        Lexer lexer = new Lexer(input);

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.FUN, tokens.get(0).getType());
        assertEquals("test", tokens.get(1).getValue());
        assertEquals(TokenType.LPAR, tokens.get(2).getType());
        assertEquals(TokenType.RPAR, tokens.get(3).getType());
        assertEquals(TokenType.LCURLY, tokens.get(4).getType());
        assertEquals(TokenType.RCURLY, tokens.get(5).getType());
    }

    @Test
    void tokenizeBoolean() {
        String input = "true\nfalse";

        Lexer lexer = new Lexer(input);

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.BOOLEAN, tokens.get(0).getType());
        assertEquals(TokenType.BOOLEAN, tokens.get(1).getType());
    }

    @Test
    void tokenizeString() {
        String input = "'Hello, World!\n'";

        Lexer lexer = new Lexer(input);

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.STRING, tokens.get(0).getType());
        assertEquals("Hello, World!\n", tokens.get(0).getValue());
    }

    @Test
    void tokenizeIfStatement() {
        String input = "if (a == 1) {}" +
                "else if (a == 2) {}" +
                "else {}";

        Lexer lexer = new Lexer(input);

        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.IF, tokens.get(0).getType());
        assertEquals(TokenType.LPAR, tokens.get(1).getType());
        assertEquals(TokenType.EQUALS, tokens.get(3).getType());
        assertEquals(TokenType.RPAR, tokens.get(5).getType());
        assertEquals(TokenType.LCURLY, tokens.get(6).getType());
        assertEquals(TokenType.RCURLY, tokens.get(7).getType());
        assertEquals(TokenType.ELSE, tokens.get(8).getType());
        assertEquals(TokenType.IF, tokens.get(9).getType());
        assertEquals(TokenType.LPAR, tokens.get(10).getType());
        assertEquals(TokenType.EQUALS, tokens.get(12).getType());
        assertEquals(TokenType.RPAR, tokens.get(14).getType());
        assertEquals(TokenType.LCURLY, tokens.get(15).getType());
        assertEquals(TokenType.RCURLY, tokens.get(16).getType());
        assertEquals(TokenType.ELSE, tokens.get(17).getType());
        assertEquals(TokenType.LCURLY, tokens.get(18).getType());
        assertEquals(TokenType.RCURLY, tokens.get(19).getType());
    }
}
