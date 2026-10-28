package com.example.poc;

/**
 * Framework-free request logic used by the web layer, so it can be unit tested
 * without a servlet container.
 */
public class CalculationHandler {

    public record Response(int status, String json) {}

    private final Calculator calc = new Calculator();

    public Response handle(String op, String a, String b) {
        if (op == null || op.isBlank()) {
            return error(400, "Missing operation");
        }
        try {
            int x = parse(a, "a");
            return switch (op.trim().toLowerCase()) {
                case "add" -> ok(String.valueOf(calc.add(x, parse(b, "b"))));
                case "subtract" -> ok(String.valueOf(calc.subtract(x, parse(b, "b"))));
                case "multiply" -> ok(String.valueOf(calc.multiply(x, parse(b, "b"))));
                case "divide" -> ok(String.valueOf(calc.divide(x, parse(b, "b"))));
                case "evenodd" -> ok("\"" + (calc.isEven(x) ? "even" : "odd") + "\"");
                default -> error(400, "Unknown operation: " + op);
            };
        } catch (IllegalArgumentException e) {
            return error(400, e.getMessage());
        }
    }

    private int parse(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing number: " + name);
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Not a valid whole number: " + name);
        }
    }

    private Response ok(String jsonValue) {
        return new Response(200, "{\"result\":" + jsonValue + "}");
    }

    private Response error(int status, String message) {
        return new Response(status, "{\"error\":\"" + escape(message) + "\"}");
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
