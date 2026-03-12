package ru.rpcot.rpscript.state;

import java.util.List;

public class StateInternalFunction extends StateBaseEntry {
    public final NativeCallable body;

    public StateInternalFunction(NativeCallable body) {
        super(StateBaseEntryType.INTERNAL_FUNCTION);
        this.body = body;
    }

    public Object execute(List<Object> args) {
        return this.body.call(args);
    }
}
