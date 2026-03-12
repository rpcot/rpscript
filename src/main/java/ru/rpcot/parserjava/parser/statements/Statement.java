package ru.rpcot.parserjava.parser.statements;

import ru.rpcot.parserjava.parser.Node;
import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.state.State;

public abstract class Statement extends Node {
    public abstract Signal execute(State state);
}
