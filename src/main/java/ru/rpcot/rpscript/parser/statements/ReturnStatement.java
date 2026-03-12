package ru.rpcot.rpscript.parser.statements;

import ru.rpcot.rpscript.parser.Signal;
import ru.rpcot.rpscript.parser.SignalType;
import ru.rpcot.rpscript.parser.expressions.Expression;
import ru.rpcot.rpscript.state.State;

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
