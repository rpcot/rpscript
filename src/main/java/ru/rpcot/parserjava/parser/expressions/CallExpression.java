package ru.rpcot.parserjava.parser.expressions;

import ru.rpcot.parserjava.parser.Signal;
import ru.rpcot.parserjava.parser.SignalType;
import ru.rpcot.parserjava.state.*;

import java.util.List;

public class CallExpression extends Expression {
    private final String name;
    private final List<Expression> args;

    public CallExpression(String name, List<Expression> args) {
        this.name = name;
        this.args = args;
    }

    public Object evaluate(State state) throws RuntimeException {
        StateBaseEntry entry = state.get(name);
        if (entry.type == StateBaseEntryType.VARIABLE) {
            throw new RuntimeException(String.format("%s is not a function", name));
        }
        List<Object> evaluatedArgs = this.args.stream().map(arg -> arg.evaluate(state)).toList();
        if (entry instanceof StateInternalFunction func) {
            return func.execute(evaluatedArgs);
        } else if (entry instanceof StateFunction func) {
            Object result = func.execute(evaluatedArgs);
            if (result instanceof Signal signal) {
                if (signal.type == SignalType.RETURN) return signal.value;
                result = null;
            }
            return result;
        }
        return null;
    }
}
