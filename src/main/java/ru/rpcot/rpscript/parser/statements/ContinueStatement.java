package ru.rpcot.rpscript.parser.statements;

import ru.rpcot.rpscript.parser.Signal;
import ru.rpcot.rpscript.state.State;

public class ContinueStatement extends Statement {
    public ContinueStatement() {
    }

    public Signal execute(State state) {
        return Signal.CONTINUE;
    }
}
