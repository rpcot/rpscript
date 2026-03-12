package ru.rpcot.parserjava.parser.statements;

import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.state.State;

public class ContinueStatement extends Statement {
    public ContinueStatement() {
    }

    public Signal execute(State state) {
        return Signal.CONTINUE;
    }
}
