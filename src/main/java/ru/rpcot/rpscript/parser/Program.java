package ru.rpcot.rpscript.parser;

import ru.rpcot.rpscript.parser.statements.Statement;
import ru.rpcot.rpscript.state.State;

import java.util.List;

public class Program extends Node {
    private final List<Statement> statements;

    public Program(List<Statement> statements) {
        this.statements = statements;
    }

    public Object execute(State state) {
        Object result = null;
        for (Statement statement : this.statements) {
            Signal signal = statement.execute(state);
            if (signal != null && signal.type == SignalType.RETURN) return signal.value;
            else if (signal != null) result = signal.value;
        }
        return result;
    }
}