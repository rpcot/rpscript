package ru.rpcot.rpscript.parser.statements;

import ru.rpcot.rpscript.parser.Signal;
import ru.rpcot.rpscript.parser.expressions.Expression;
import ru.rpcot.rpscript.state.State;

import java.util.List;

public class IfStatement extends Statement {
    private final List<Expression> conditions;
    private final List<Statement> thenBranches;
    private final Statement elseBranch;

    public IfStatement(List<Expression> conditions, List<Statement> thenBranches, Statement elseBranch) {
        this.conditions = conditions;
        this.thenBranches = thenBranches;
        this.elseBranch = elseBranch;
    }

    public Signal execute(State state) {
        for (int i = 0; i < this.conditions.size(); i++) {
            if ((boolean) this.conditions.get(i).evaluate(state)) {
                return this.thenBranches.get(i).execute(state);
            }
        }
        if (this.elseBranch != null) {
            return this.elseBranch.execute(state);
        }
        return null;
    }
}
