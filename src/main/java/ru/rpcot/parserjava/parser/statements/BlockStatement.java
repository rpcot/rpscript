package ru.rpcot.parserjava.parser.statements;

import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.parser.SignalType;
import ru.rpcot.parserjava.state.State;

import java.util.List;

public class BlockStatement extends Statement {
    private final List<Statement> statements;

    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }

    public Signal execute(State state) {
        for (Statement statement : statements) {
            Signal signal = statement.execute(state);
            if (signal != null) return signal;
        }
        return null;
    }
}
