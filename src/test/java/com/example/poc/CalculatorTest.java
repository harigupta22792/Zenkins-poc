package com.example.poc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculatorTest {

    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    @Test
    @DisplayName("add returns the sum")
    void addTest() {
        assertEquals(15, calc.add(10, 5));
    }

    @Test
    void subtractTest() {
        assertEquals(5, calc.subtract(10, 5));
    }

    @Test
    void multiplyTest() {
        assertEquals(50, calc.multiply(10, 5));
    }

    @Test
    void divideTest() {
        assertEquals(2.5, calc.divide(10, 4), 0.0001);
    }

    @Test
    @DisplayName("divide by zero throws")
    void divideByZeroTest() {
        assertThrows(IllegalArgumentException.class, () -> calc.divide(1, 0));
    }

    @ParameterizedTest
    @CsvSource({"2,true", "3,false", "0,true", "-4,true", "-7,false"})
    void isEvenTest(int input, boolean expected) {
        assertEquals(expected, calc.isEven(input));
    }
}
