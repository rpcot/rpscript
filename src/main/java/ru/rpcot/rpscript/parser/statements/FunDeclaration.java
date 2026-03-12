package ru.rpcot.rpscript.parser.statements;

import ru.rpcot.rpscript.parser.Signal;
import ru.rpcot.rpscript.state.State;
import ru.rpcot.rpscript.state.StateFunction;

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
