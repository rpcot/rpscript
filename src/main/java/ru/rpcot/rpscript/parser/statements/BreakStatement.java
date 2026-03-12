package ru.rpcot.rpscript.parser.statements;

import ru.rpcot.rpscript.parser.Signal;
import ru.rpcot.rpscript.state.State;

public class BreakStatement extends Statement {
    public BreakStatement() {
    }

    public Signal execute(State state) {
        return Signal.BREAK;
    }
}
