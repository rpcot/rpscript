package ru.rpcot.parserjava.state;

import java.util.List;

@FunctionalInterface
public interface NativeCallable {
    Object call(List<Object> args);
}