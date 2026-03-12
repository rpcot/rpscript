package ru.rpcot.rpscript.interpreter;

import ru.rpcot.rpscript.parser.Program;
import ru.rpcot.rpscript.state.State;

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
