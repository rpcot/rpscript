package ru.rpcot.parserjava.parser.statements;

import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.state.State;
import ru.rpcot.parserjava.state.StateFunction;

import java.util.List;

public class FunDeclaration extends Statement {
    private final String name;
    private final List<String> params;
    private final Statement body;

    public FunDeclaration(String name, List<String> params, Statement body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public Signal execute(State state) {
        StateFunction func = new StateFunction(this.params, this.body, state);
        state.define(this.name, func);
        return null;
    }
}
