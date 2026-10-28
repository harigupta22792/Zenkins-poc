package com.example.poc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class AppTest {

    private String run(String userInput) {
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        new App(in, new PrintStream(buffer, true, StandardCharsets.UTF_8)).run();
        return buffer.toString(StandardCharsets.UTF_8);
    }

    @Test
    void addFlow() {
        assertTrue(run("1\n10\n5\n0\n").contains("Result: 15"));
    }

    @Test
    void divideByZeroShowsError() {
        assertTrue(run("4\n1\n0\n0\n").contains("Cannot divide by zero"));
    }

    @Test
    void invalidMenuOption() {
        assertTrue(run("9\n0\n").contains("Invalid option"));
    }

    @Test
    void invalidNumberIsReprompted() {
        String output = run("1\nabc\n2\n3\n0\n");
        assertTrue(output.contains("valid whole number"));
        assertTrue(output.contains("Result: 5"));
    }

    @Test
    void evenOddFlow() {
        assertTrue(run("5\n7\n0\n").contains("7 is odd"));
    }

    @Test
    void exitOption() {
        assertTrue(run("0\n").contains("Goodbye!"));
    }

    @Test
    void endOfInputExitsCleanly() {
        assertTrue(run("").contains("Input ended"));
    }
}
