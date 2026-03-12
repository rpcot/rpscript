package ru.rpcot.parserjava.parser.statements;

import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.parser.expressions.Expression;
import ru.rpcot.parserjava.state.State;
import ru.rpcot.parserjava.state.StateVariable;

public class VariableDeclaration extends Statement {
    private final String name;
    private final Expression expression;
    private final boolean isConstant;

    public VariableDeclaration(String name, Expression expression, boolean isConstant) {
        this.name = name;
        this.expression = expression;
        this.isConstant = isConstant;
    }

    public Signal execute(State state) {
        Object value = this.expression.evaluate(state);
        StateVariable entry = new StateVariable(value, isConstant);
        state.define(name, entry);
        return null;
    }
}