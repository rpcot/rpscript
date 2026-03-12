package ru.rpcot.rpscript.state;

import java.util.HashMap;
import java.util.Map;

public class State {
    private final Map<String, StateBaseEntry> variables = new HashMap<>();
    private final State parent;

    public State(State parent) {
        this.parent = parent;
    }

    public void define(String name, StateBaseEntry value) {
        if (variables.containsKey(name)) {
            throw new RuntimeException(String.format("%s is already defined", name));
        } else {
            variables.put(name, value);
        }
    }

    public void assign(String name, StateBaseEntry value) throws RuntimeException {
        if (variables.containsKey(name)) {
            StateBaseEntry entryValue = get(name);
            if (entryValue instanceof StateVariable variable && variable.isConstant) {
                throw new RuntimeException(String.format("assignment to const value %s", name));
            }
            variables.put(name, value);
        } else if (parent != null) {
            parent.assign(name, value);
        } else {
            throw new RuntimeException(String.format("%s is not defined", name));
        }
    }

    public StateBaseEntry get(String name) throws RuntimeException {
        if (variables.containsKey(name)) return variables.get(name);
        else if (parent != null) return parent.get(name);
        throw new RuntimeException(String.format("%s is not defined", name));
    }
}
