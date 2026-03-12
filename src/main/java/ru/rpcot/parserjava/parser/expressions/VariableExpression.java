package ru.rpcot.parserjava.parser.expressions;

import ru.rpcot.parserjava.state.*;

public class VariableExpression extends Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    public Object evaluate(State state) {
        StateBaseEntry entry = state.get(this.name);
        if (entry instanceof StateVariable variable) {
            return variable.value;
        } else if (entry instanceof StateInternalFunction) {
            return String.format("<internal function '%s'>", name);
        } else {
            return null;
        }
    }
}