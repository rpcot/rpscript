package ru.rpcot.rpscript.parser.expressions;

import ru.rpcot.rpscript.parser.Node;
import ru.rpcot.rpscript.state.State;

public abstract class Expression extends Node {
    public abstract Object evaluate(State state);
}