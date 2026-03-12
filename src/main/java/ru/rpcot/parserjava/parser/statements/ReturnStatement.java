package ru.rpcot.parserjava.parser.statements;

import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.parser.SignalType;
import ru.rpcot.parserjava.parser.expressions.Expression;
import ru.rpcot.parserjava.state.State;

public class ReturnStatement extends Statement {
    private final Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    public Signal execute(State state) {
        Object value = this.expression.evaluate(state);
        return new Signal(SignalType.RETURN, value);
    }
}
