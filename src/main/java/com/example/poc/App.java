package com.example.poc;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Interactive console calculator.
 * Input/output streams are injected so the menu flow can be unit tested.
 */
public class App {

    private final Scanner in;
    private final PrintStream out;
    private final Calculator calc = new Calculator();

    public App(InputStream input, PrintStream out) {
        this.in = new Scanner(input);
        this.out = out;
    }

    public static void main(String[] args) {
        new App(System.in, System.out).run();
    }

    public void run() {
        out.println("=== Java Jenkins POC Calculator ===");
        try {
            boolean running = true;
            while (running) {
                printMenu();
                String choice = prompt("Choose an option: ").trim();
                switch (choice) {
                    case "1" -> out.println("Result: " + calc.add(readInt("First number: "), readInt("Second number: ")));
                    case "2" -> out.println("Result: " + calc.subtract(readInt("First number: "), readInt("Second number: ")));
                    case "3" -> out.println("Result: " + calc.multiply(readInt("First number: "), readInt("Second number: ")));
                    case "4" -> divide();
                    case "5" -> {
                        int n = readInt("Number: ");
                        out.println(n + " is " + (calc.isEven(n) ? "even" : "odd"));
                    }
                    case "0" -> {
                        out.println("Goodbye!");
                        running = false;
                    }
                    default -> out.println("Invalid option, please try again.");
                }
            }
        } catch (NoSuchElementException e) {
            out.println();
            out.println("Input ended. Goodbye!");
        }
    }

    private void divide() {
        int a = readInt("First number: ");
        int b = readInt("Second number: ");
        try {
            out.println("Result: " + calc.divide(a, b));
        } catch (IllegalArgumentException e) {
            out.println("Error: " + e.getMessage());
        }
    }

    private void printMenu() {
        out.println();
        out.println("1) Add");
        out.println("2) Subtract");
        out.println("3) Multiply");
        out.println("4) Divide");
        out.println("5) Even or odd");
        out.println("0) Exit");
    }

    private String prompt(String label) {
        out.print(label);
        out.flush();
        return in.nextLine();
    }

    private int readInt(String label) {
        while (true) {
            String text = prompt(label).trim();
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                out.println("Please enter a valid whole number.");
            }
        }
    }
}
