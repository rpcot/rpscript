package ru.rpcot.parserjava.parser.expressions;

import ru.rpcot.parserjava.lexer.TokenType;
import ru.rpcot.parserjava.state.State;

import java.util.Objects;

public class BinaryExpression extends Expression {
    private final TokenType op;
    private final Expression lhs;
    private final Expression rhs;

    public BinaryExpression(Expression lhs, TokenType op, Expression rhs) {
        this.lhs = lhs;
        this.op = op;
        this.rhs = rhs;
    }

    public Object evaluate(State state) {
        Object lhsValue = lhs.evaluate(state);
        Object rhsValue = rhs.evaluate(state);
        switch (op) {
            case TokenType.PLUS: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left + right;
                } else {
                    return String.valueOf(lhsValue) + String.valueOf(rhsValue);
                }
            }
            case TokenType.MINUS: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left - right;
                } else {
                    throw new RuntimeException(String.format("cannot minus two expressions: %s + %s", String.valueOf(lhsValue), String.valueOf(rhsValue)));
                }
            }
            case TokenType.DIV: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left / right;
                } else {
                    throw new RuntimeException(String.format("cannot div two expressions: %s + %s", String.valueOf(lhsValue), String.valueOf(rhsValue)));
                }
            }
            case TokenType.MUL: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left * right;
                } else {
                    throw new RuntimeException(String.format("cannot mul two expressions: %s + %s", String.valueOf(lhsValue), String.valueOf(rhsValue)));
                }
            }
            case TokenType.POW: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return Math.pow(left, right);
                } else {
                    throw new RuntimeException(String.format("cannot mul two expressions: %s + %s", String.valueOf(lhsValue), String.valueOf(rhsValue)));
                }
            }
            case TokenType.EQUALS: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left.equals(right);
                } else {
                    return Objects.equals(String.valueOf(lhsValue), String.valueOf(rhsValue));
                }
            }
            case TokenType.NOT_EQUALS: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return !left.equals(right);
                } else {
                    return !Objects.equals(String.valueOf(lhsValue), String.valueOf(rhsValue));
                }
            }
            case TokenType.AND: {
                if (lhsValue instanceof Boolean left && rhsValue instanceof Boolean right) {
                    return left && right;
                } else {
                    throw new RuntimeException(String.format("cannot and two expressions: %s + %s", String.valueOf(lhsValue), String.valueOf(rhsValue)));
                }
            }
            case TokenType.OR: {
                if (lhsValue instanceof Boolean left && rhsValue instanceof Boolean right) {
                    return left || right;
                } else {
                    throw new RuntimeException(String.format("cannot or two expressions: %s + %s", String.valueOf(lhsValue), String.valueOf(rhsValue)));
                }
            }
            case TokenType.GREATER: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left > right;
                } else {
                    throw new RuntimeException(String.format("cannot greater two expressions: %s + %s", String.valueOf(lhsValue), String.valueOf(rhsValue)));
                }
            }
            case TokenType.GREATER_EQUALS: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left >= right;
                } else {
                    throw new RuntimeException(String.format("cannot greater equals two expressions: %s + %s", String.valueOf(lhsValue), String.valueOf(rhsValue)));
                }
            }
            case TokenType.LESS: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left < right;
                } else {
                    throw new RuntimeException(String.format("cannot less two expressions: %s + %s", String.valueOf(lhsValue), String.valueOf(rhsValue)));
                }
            }
            case TokenType.LESS_EQUALS: {
                if (lhsValue instanceof Double left && rhsValue instanceof Double right) {
                    return left <= right;
                } else {
                    throw new RuntimeException(String.format("cannot less equals equals two expressions: %s + %s", String.valueOf(lhsValue), String.valueOf(rhsValue)));
                }
            }
            default:
                throw new RuntimeException(String.format("unknown operator type %s", op));
        }
    }
}
