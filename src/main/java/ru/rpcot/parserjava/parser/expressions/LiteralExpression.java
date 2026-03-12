package ru.rpcot.parserjava.parser.expressions;

import ru.rpcot.parserjava.state.State;

public class LiteralExpression extends Expression {
    private final Object value;

    public LiteralExpression(Object value) {
        this.value = value;
    }

    public Object evaluate(State state) {
        return this.value;
    }
}
