package ru.rpcot.rpscript.state;

import java.util.List;

@FunctionalInterface
public interface NativeCallable {
    Object call(List<Object> args);
}