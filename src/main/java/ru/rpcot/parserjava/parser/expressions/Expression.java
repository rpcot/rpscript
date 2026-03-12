package ru.rpcot.parserjava.parser.expressions;

import ru.rpcot.parserjava.parser.Node;
import ru.rpcot.parserjava.state.State;

public abstract class Expression extends Node {
    public abstract Object evaluate(State state);
}