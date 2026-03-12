package ru.rpcot.parserjava.parser.statements;

import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.parser.expressions.Expression;
import ru.rpcot.parserjava.state.State;
import ru.rpcot.parserjava.state.StateVariable;

public class VariableAssignment extends Statement {
    private final String name;
    private final Expression expression;

    public VariableAssignment(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public Signal execute(State state) {
        Object value = this.expression.evaluate(state);
        StateVariable entry = new StateVariable(value, false);
        state.assign(name, entry);
        return null;
    }
}
