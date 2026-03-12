package ru.rpcot.rpscript;

import org.junit.jupiter.api.Test;
import ru.rpcot.rpscript.interpreter.Interpreter;
import ru.rpcot.rpscript.lexer.Lexer;
import ru.rpcot.rpscript.lexer.Token;
import ru.rpcot.rpscript.lib.StandardLib;
import ru.rpcot.rpscript.parser.Parser;
import ru.rpcot.rpscript.parser.Program;
import ru.rpcot.rpscript.state.State;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RpscriptTest {
    @Test
    void testMath1() {
        String input = "return (3 - 1 ** 4) + 2 * 2";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Program program = parser.parseProgram();
        State state = new State(null);
        StandardLib.register(state);
        Interpreter interpreter = new Interpreter(state);
        Object result = interpreter.run(program);

        assertEquals(6.0, result);
    }

    @Test
    void testMath2() {
        String input = "return sqrt(4) + 8 * 2";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Program program = parser.parseProgram();
        State state = new State(null);
        StandardLib.register(state);
        Interpreter interpreter = new Interpreter(state);
        Object result = interpreter.run(program);

        assertEquals(18.0, result);
    }

    @Test
    void testFun() {
        String input = "fun greeting(name) {" +
                "print('Hello,')" +
                "print(name)" +
                '}' +
                "greeting('Kostya')";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Program program = parser.parseProgram();
        State state = new State(null);
        StandardLib.register(state);
        Interpreter interpreter = new Interpreter(state);
        Object result = interpreter.run(program);

        assertEquals(null, result);
    }

    @Test
    void testExpr() {
        String input = "return 2 + 2 * 2";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Program program = parser.parseProgram();
        State state = new State(null);
        StandardLib.register(state);
        Interpreter interpreter = new Interpreter(state);
        Object result = interpreter.run(program);

        assertEquals(6.0, result);
    }

    @Test
    void testReturn1() {
        String input = "fun get228() {" +
                "return 228" +
                "}" +
                "return get228()";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Program program = parser.parseProgram();
        State state = new State(null);
        StandardLib.register(state);
        Interpreter interpreter = new Interpreter(state);
        Object result = interpreter.run(program);

        assertEquals(228.0, result);
    }

    @Test
    void testReturn2() {
        String input = "fun test() return 451" +
                "fun get228() {" +
                "return test()" +
                "}" +
                "const a = get228()" +
                "return a + a * 2";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Program program = parser.parseProgram();
        State state = new State(null);
        StandardLib.register(state);
        Interpreter interpreter = new Interpreter(state);
        Object result = interpreter.run(program);

        assertEquals(1353.0, result);
    }

    @Test
    void testIf() {
        String input = "if ('test' == 'test') {" +
                "return true" +
                "} else if ('test' == 'test') {" +
                "return false" +
                "}";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Program program = parser.parseProgram();
        State state = new State(null);
        StandardLib.register(state);
        Interpreter interpreter = new Interpreter(state);
        Object result = interpreter.run(program);

        assertEquals(true, result);
    }
}
