package ru.rpcot.parserjava.parser.statements;

import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.state.State;

public class BreakStatement extends Statement {
    public BreakStatement() {
    }

    public Signal execute(State state) {
        return Signal.BREAK;
    }
}
