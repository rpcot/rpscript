package ru.rpcot.parserjava.lexer;

public enum TokenType {
    IDENTIFIER,
    NUMBER,
    BOOLEAN,
    STRING,

    ASSIGN,
    EQUALS,
    NOT_EQUALS,
    PLUS,
    MINUS,
    MUL,
    DIV,
    POW,
    GREATER,
    GREATER_EQUALS,
    LESS,
    LESS_EQUALS,
    AND,
    OR,

    LPAR,
    RPAR,
    LCURLY,
    RCURLY,
    COMMA,
    SEMICOLON,

    FUN,
    VAR,
    CONST,
    RETURN,
    VOID,
    IF,
    ELSE,
    BREAK,
    CONTINUE,
    FOR,

    EOF,
}
