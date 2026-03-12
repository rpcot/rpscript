package ru.rpcot.parserjava.parser.expressions;

import ru.rpcot.parserjava.state.State;

public class VoidExpression extends Expression {
    public VoidExpression() {
    }

    public Object evaluate(State state) {
        return null;
    }
}
