package ru.rpcot.rpscript.state;

import ru.rpcot.rpscript.parser.statements.Statement;

import java.util.List;

public class StateFunction extends StateBaseEntry {
    private final List<String> params;
    private final Statement body;
    private final State state;

    public StateFunction(List<String> params, Statement body, State state) {
        super(StateBaseEntryType.FUNCTION);
        this.params = params;
        this.body = body;
        this.state = state;
    }

    public Object execute(List<Object> args) {
        State localState = new State(this.state);
        for (int i = 0; i < params.size(); i++) {
            Object value = (args.size() > i) ? args.get(i) : null;
            localState.define(params.get(i), new StateVariable(value, false));
        }
        return this.body.execute(localState);
    }
}
