package ru.rpcot.parserjava.parser.expressions;

import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.parser.statements.Statement;
import ru.rpcot.parserjava.state.State;

public class ExpressionStatement extends Statement {
    private final Expression expression;

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }

    public Signal execute(State state) {
        Object result = this.expression.evaluate(state);
        if (result instanceof Signal signal) {
            return signal;
        }
        return null;
    }
}
