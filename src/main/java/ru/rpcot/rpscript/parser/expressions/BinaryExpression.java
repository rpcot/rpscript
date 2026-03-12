package ru.rpcot.rpscript.parser.expressions;

import ru.rpcot.rpscript.lexer.Token;
import ru.rpcot.rpscript.state.State;

import java.util.Objects;

public class BinaryExpression extends Expression {
    private final Token op;
    private final Expression lhs;
    private final Expression rhs;

    public BinaryExpression(Expression lhs, Token op, Expression rhs) {
        this.lhs = lhs;
        this.op = op;
        this.rhs = rhs;
    }

    public Object evaluate(State state) {
        Object lhsValue = lhs.evaluate(state);
        Object rhsValue = rhs.evaluate(state);

        final boolean equals = Objects.equals(String.valueOf(lhsValue), String.valueOf(rhsValue));

        switch (op.getType()) {
            case PLUS -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left + right;
                } else {
                    return String.valueOf(lhsValue) + rhsValue;
                }
            }
            case MINUS -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left - right;
                } else {
                    throw new RuntimeException(String.format("cannot minus two expressions: %s - %s", lhsValue, rhsValue));
                }
            }
            case DIV -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left / right;
                } else {
                    throw new RuntimeException(String.format("cannot div two expressions: %s / %s", lhsValue, rhsValue));
                }
            }
            case MUL -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left * right;
                } else {
                    throw new RuntimeException(String.format("cannot mul two expressions: %s * %s", lhsValue, rhsValue));
                }
            }
            case POW -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return Math.pow(left, right);
                } else {
                    throw new RuntimeException(String.format("cannot mul two expressions: %s ** %s", lhsValue, rhsValue));
                }
            }
            case EQUALS -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left.equals(right);
                } else {
                    return equals;
                }
            }
            case NOT_EQUALS -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return !left.equals(right);
                } else {
                    return !equals;
                }
            }
            case AND -> {
                if (lhsValue instanceof Boolean left && rhsValue instanceof Boolean right) {
                    return left && right;
                } else {
                    throw new RuntimeException(String.format("cannot and two expressions: %s && %s", lhsValue, rhsValue));
                }
            }
            case OR -> {
                if (lhsValue instanceof Boolean left && rhsValue instanceof Boolean right) {
                    return left || right;
                } else {
                    throw new RuntimeException(String.format("cannot or two expressions: %s || %s", lhsValue, rhsValue));
                }
            }
            case GREATER -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left > right;
                } else {
                    throw new RuntimeException(String.format("cannot greater two expressions: %s > %s", lhsValue, rhsValue));
                }
            }
            case GREATER_EQUALS -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left >= right;
                } else {
                    throw new RuntimeException(String.format("cannot greater equals two expressions: %s >= %s", lhsValue, rhsValue));
                }
            }
            case LESS -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left < right;
                } else {
                    throw new RuntimeException(String.format("cannot less two expressions: %s < %s", lhsValue, rhsValue));
                }
            }
            case LESS_EQUALS -> {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left <= right;
                } else {
                    throw new RuntimeException(String.format("cannot less equals equals two expressions: %s <= %s", lhsValue, rhsValue));
                }
            }
            default ->
                    throw new RuntimeException(String.format("unknown operator type %s at line %d", op.getType(), op.getLine()));
        }
    }
}
