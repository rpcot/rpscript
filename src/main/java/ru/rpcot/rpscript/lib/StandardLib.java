package ru.rpcot.rpscript.lib;

import ru.rpcot.rpscript.state.State;
import ru.rpcot.rpscript.state.StateInternalFunction;

import java.util.List;
import java.util.Scanner;

public class StandardLib {
    private static String valueOf(Object value) {
        if (value instanceof Double d) {
            if (d == d.longValue()) {
                return String.valueOf(d.longValue());
            }
        }
        return String.valueOf(value);
    }

    public static void register(State state) throws RuntimeException {
        state.define("print", new StateInternalFunction(args -> {
            List<String> strArgs = args.stream().map(StandardLib::valueOf).toList();
            String result = String.join(" ", strArgs);
            System.out.println(result);
            return null;
        }));
        state.define("exit", new StateInternalFunction(args -> {
            if (!args.isEmpty() && args.getFirst() instanceof Double d) {
                System.exit(d.intValue());
            }
            System.exit(0);
            return null;
        }));
        state.define("sqrt", new StateInternalFunction(args -> {
            if (!args.isEmpty() && args.getFirst() instanceof Double d) {
                return Math.sqrt(d);
            }
            throw new RuntimeException(String.format("cannot use sqrt to %s", args.getFirst()));
        }));
        state.define("read", new StateInternalFunction(args -> {
            if (!args.isEmpty()) {
                System.out.print(args.getFirst());
            }
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        }));
        state.define("number", new StateInternalFunction(args -> {
            if (!args.isEmpty() && args.getFirst() instanceof String str) {
                try {
                    return Double.parseDouble(str);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(String.format("cannot resolve to number: %s", args.getFirst()));
                }
            }
            throw new RuntimeException(String.format("unexpected value %s", args.getFirst()));
        }));
    }
}
