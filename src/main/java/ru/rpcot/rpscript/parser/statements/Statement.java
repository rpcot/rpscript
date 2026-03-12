package ru.rpcot.rpscript.parser.statements;

import ru.rpcot.rpscript.parser.Node;
import ru.rpcot.rpscript.parser.Signal;
import ru.rpcot.rpscript.state.State;

public abstract class Statement extends Node {
    public abstract Signal execute(State state);
}
