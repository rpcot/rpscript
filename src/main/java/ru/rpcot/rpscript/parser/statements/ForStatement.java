package ru.rpcot.rpscript.parser.statements;

import ru.rpcot.rpscript.parser.Signal;
import ru.rpcot.rpscript.parser.SignalType;
import ru.rpcot.rpscript.parser.expressions.Expression;
import ru.rpcot.rpscript.state.State;

public class ForStatement extends Statement {
    private final Statement init;
    private final Expression condition;
    private final Statement update;
    private final Statement body;

    public ForStatement(Statement init, Expression condition, Statement update, Statement body) {
        this.init = init;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }

    public Signal execute(State state) {
        State localState = new State(state);
        if (this.init != null) {
            this.init.execute(localState);
        }
        boolean next = true;
        if (this.condition != null) {
            next = (boolean) this.condition.evaluate(localState);
        }
        while (next) {
            Signal signal = this.body.execute(localState);
            if (signal instanceof Signal s) {
                if (s.type == SignalType.RETURN) return s;
                else if (s.type == SignalType.BREAK) break;
            }
            if (this.update != null) {
                this.update.execute(localState);
            }
            if (this.condition != null) {
                next = (boolean) this.condition.evaluate(localState);
            }
        }
        return null;
    }
}
