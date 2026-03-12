package ru.rpcot.rpscript.parser.expressions;

import ru.rpcot.rpscript.state.State;

public class VoidExpression extends Expression {
    public VoidExpression() {
    }

    public Object evaluate(State state) {
        return null;
    }
}
