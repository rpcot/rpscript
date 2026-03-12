package ru.rpcot.parserjava.parser;

public class Signal {
    public final SignalType type;
    public final Object value;

    public static final Signal BREAK = new Signal(SignalType.BREAK, null);
    public static final Signal CONTINUE = new Signal(SignalType.CONTINUE, null);

    public Signal(SignalType type, Object value) {
        this.type = type;
        this.value = value;
    }
}