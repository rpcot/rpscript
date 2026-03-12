package ru.rpcot.parserjava;

import ru.rpcot.parserjava.interpreter.Interpreter;
import ru.rpcot.parserjava.lexer.Lexer;
import ru.rpcot.parserjava.lexer.Token;
import ru.rpcot.parserjava.lib.StandardLib;
import ru.rpcot.parserjava.parser.Parser;
import ru.rpcot.parserjava.parser.Program;
import ru.rpcot.parserjava.state.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: rp <file>");
            return;
        }

        try {
            Path filePath = Path.of(args[0]);
            String code = Files.readString(filePath);

            Lexer lexer = new Lexer(code);
            List<Token> tokens = lexer.tokenize();

            Program program = new Parser(tokens).parseProgram();

            State state = new State(null);
            StandardLib.register(state);
            Interpreter interpreter = new Interpreter(state);

            interpreter.run(program);
        } catch (IOException e) {
            System.err.println("Error: cannot read file " + args[0]);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}
