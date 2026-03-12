package ru.rpcot.parserjava.interpreter;

import ru.rpcot.parserjava.parser.Program;
import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.state.State;

public class Interpreter {
    private final State state;

    public Interpreter(State state) {
        this.state = state;
    }

    public Object run(Program program) {
        Object result = program.execute(state);
        return result;
    }
}
