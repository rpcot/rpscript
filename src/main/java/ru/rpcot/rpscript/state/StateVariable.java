package ru.rpcot.rpscript.state;

public class StateVariable extends StateBaseEntry {
    public final Object value;
    public final boolean isConstant;

    public StateVariable(Object value, boolean isConstant) {
        super(StateBaseEntryType.VARIABLE);
        this.value = value;
        this.isConstant = isConstant;
    }
}
