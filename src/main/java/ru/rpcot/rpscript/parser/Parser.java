package ru.rpcot.rpscript.parser;

import ru.rpcot.rpscript.lexer.Token;
import ru.rpcot.rpscript.lexer.TokenType;
import ru.rpcot.rpscript.parser.expressions.*;
import ru.rpcot.rpscript.parser.statements.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int currentIndex = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private double[] getBp(TokenType type) {
        return switch (type) {
            case AND, OR -> new double[]{0.2, 0.3};
            case EQUALS, NOT_EQUALS -> new double[]{0.4, 0.5};
            case GREATER, GREATER_EQUALS, LESS, LESS_EQUALS -> new double[]{0.6, 0.7};
            case MINUS, PLUS -> new double[]{1, 1.1};
            case MUL, DIV -> new double[]{2, 2.1};
            case POW -> new double[]{3, 2.9};
            case LPAR -> new double[]{5, 0};
            default -> new double[]{0, 0};
        };
    }

    private Token peek(int offset) {
        return tokens.get(this.currentIndex + offset);
    }

    private Token peek() {
        return peek(0);
    }

    private Token consume(TokenType type) {
        Token token = peek();
        if (token.getType() == type) {
            this.currentIndex++;
            return token;
        } else {
            throw new RuntimeException(String.format("expected %s but got %s at line %d", type, token.getType(), token.getLine()));
        }
    }

    private Expression parseExpression(double bp) {
        Token token = peek();
        Expression lhs = switch (token.getType()) {
            case NUMBER -> new LiteralExpression(Double.parseDouble(token.getValue()));
            case BOOLEAN -> new LiteralExpression(Boolean.parseBoolean(token.getValue()));
            case STRING -> new LiteralExpression(token.getValue());
            case NULL -> new LiteralExpression(null);
            case IDENTIFIER -> new VariableExpression(token.getValue());
            default -> null;
        };
        switch (token.getType()) {
            case IDENTIFIER, NUMBER, BOOLEAN, STRING, NULL -> consume(token.getType());
            case LPAR -> {
                consume(TokenType.LPAR);
                lhs = parseExpression();
                consume(TokenType.RPAR);
            }
            default -> throw new RuntimeException(String.format("unexpected token: %s", token.getType()));
        }
        while (true) {
            Token op = peek();

            if (op.getType() == TokenType.EOF) break;
            else if (op.getType() == TokenType.RPAR) break;
            else if (op.getType() == TokenType.RCURLY) break;
            else if (op.getType() == TokenType.COMMA) break;
            else if (op.getType() == TokenType.SEMICOLON) break;

            double[] opBp = getBp(op.getType());
            if (opBp[0] == 0) break;

            if (opBp[0] < bp) {
                break;
            }

            consume(op.getType());

            if (op.getType() == TokenType.LPAR) {
                lhs = parseCallExpression(token.getValue());
            } else {
                Expression rhs = parseExpression(opBp[1]);
                lhs = new BinaryExpression(lhs, op, rhs);
            }
        }
        return lhs;
    }

    private Expression parseExpression() {
        return parseExpression(0);
    }

    private Statement parseVariableDeclaration(boolean isConstant) {
        if (isConstant) {
            consume(TokenType.CONST);
        } else {
            consume(TokenType.VAR);
        }
        Token identifier = consume(TokenType.IDENTIFIER);
        String name = identifier.getValue();
        consume(TokenType.ASSIGN);
        Expression expression = parseExpression();
        return new VariableDeclaration(name, expression, isConstant);
    }

    private Statement parseVariableAssignment() {
        Token identifier = consume(TokenType.IDENTIFIER);
        String name = identifier.getValue();
        consume(TokenType.ASSIGN);
        Expression expression = parseExpression();
        return new VariableAssignment(name, expression);
    }

    private Statement parseReturnStatement() {
        consume(TokenType.RETURN);
        Expression expression;
        if (peek().getType() == TokenType.VOID) {
            consume(TokenType.VOID);
            expression = new VoidExpression();
        } else {
            expression = parseExpression();
        }
        return new ReturnStatement(expression);
    }

    private Expression parseCallExpression(String name) {
        List<Expression> args = new ArrayList<>();
        while (peek().getType() != TokenType.RPAR) {
            args.add(parseExpression());
            if (peek().getType() == TokenType.COMMA) {
                consume(TokenType.COMMA);
            }
        }
        consume(TokenType.RPAR);
        return new CallExpression(name, args);
    }

    private Statement parseBlockStatement() {
        consume(TokenType.LCURLY);
        List<Statement> statements = new ArrayList<>();
        while (peek().getType() != TokenType.RCURLY) {
            statements.add(parseStatement());
        }
        consume(TokenType.RCURLY);
        return new BlockStatement(statements);
    }

    private Statement parseFunDeclaration() {
        consume(TokenType.FUN);
        Token identifier = consume(TokenType.IDENTIFIER);
        String name = identifier.getValue();
        consume(TokenType.LPAR);
        List<String> params = new ArrayList<>();
        while (peek().getType() != TokenType.RPAR) {
            Token token = consume(TokenType.IDENTIFIER);
            params.add(token.getValue());
            if (peek().getType() == TokenType.COMMA) {
                consume(TokenType.COMMA);
            }
        }
        consume(TokenType.RPAR);
        Statement body = parseStatement();
        return new FunDeclaration(name, params, body);
    }

    private Statement parseIfStatement() {
        List<Expression> conditions = new ArrayList<>();
        List<Statement> thenBranches = new ArrayList<>();
        Statement elseBranch = null;
        while (peek().getType() == TokenType.IF) {
            consume(TokenType.IF);
            consume(TokenType.LPAR);
            conditions.add(parseExpression());
            consume(TokenType.RPAR);
            thenBranches.add(parseStatement());
            if (peek().getType() == TokenType.ELSE && peek(1).getType() == TokenType.IF) {
                consume(TokenType.ELSE);
            }
        }
        if (peek().getType() == TokenType.ELSE) {
            consume(TokenType.ELSE);
            elseBranch = parseStatement();
        }
        return new IfStatement(conditions, thenBranches, elseBranch);
    }

    private Statement parseForStatement() {
        consume(TokenType.FOR);
        consume(TokenType.LPAR);
        Statement init = null;
        Expression condition = null;
        Statement update = null;
        if (peek().getType() != TokenType.SEMICOLON) {
            init = parseStatement();
        }
        consume(TokenType.SEMICOLON);
        if (peek().getType() != TokenType.SEMICOLON) {
            condition = parseExpression();
        }
        consume(TokenType.SEMICOLON);
        if (peek().getType() != TokenType.RPAR) {
            update = parseStatement();
        }
        consume(TokenType.RPAR);
        Statement body = parseStatement();
        return new ForStatement(init, condition, update, body);
    }

    private Statement parseStatement() {
        Token token = peek();

        if (token.getType() == TokenType.VAR) {
            return parseVariableDeclaration(false);
        }

        if (token.getType() == TokenType.CONST) {
            return parseVariableDeclaration(true);
        }

        if (token.getType() == TokenType.IDENTIFIER) {
            if (peek(1).getType() == TokenType.ASSIGN) {
                return parseVariableAssignment();
            }
        }

        if (token.getType() == TokenType.RETURN) {
            return parseReturnStatement();
        }

        if (token.getType() == TokenType.BREAK) {
            consume(TokenType.BREAK);
            return new BreakStatement();
        }

        if (token.getType() == TokenType.CONTINUE) {
            consume(TokenType.CONTINUE);
            return new ContinueStatement();
        }

        if (token.getType() == TokenType.FUN) {
            return parseFunDeclaration();
        }

        if (token.getType() == TokenType.IF) {
            return parseIfStatement();
        }

        if (token.getType() == TokenType.LCURLY) {
            return parseBlockStatement();
        }

        if (token.getType() == TokenType.FOR) {
            return parseForStatement();
        }

        Expression expression = parseExpression();
        return new ExpressionStatement(expression);
    }

    public Program parseProgram() {
        List<Statement> statements = new ArrayList<>();
        while (peek().getType() != TokenType.EOF) {
            statements.add(parseStatement());
        }
        return new Program(statements);
    }
}
