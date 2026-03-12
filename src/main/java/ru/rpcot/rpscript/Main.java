package ru.rpcot.rpscript;

import ru.rpcot.rpscript.interpreter.Interpreter;
import ru.rpcot.rpscript.lexer.Lexer;
import ru.rpcot.rpscript.lexer.Token;
import ru.rpcot.rpscript.lib.StandardLib;
import ru.rpcot.rpscript.parser.Parser;
import ru.rpcot.rpscript.parser.Program;
import ru.rpcot.rpscript.state.State;

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
