package ru.rpcot.parserjava.state;

public abstract class StateBaseEntry {
    public final StateBaseEntryType type;

    public StateBaseEntry(StateBaseEntryType type) {
        this.type = type;
    }
}