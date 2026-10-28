package com.example.poc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CalculationHandlerTest {

    private final CalculationHandler handler = new CalculationHandler();

    @Test
    void addReturnsResult() {
        CalculationHandler.Response r = handler.handle("add", "10", "5");
        assertEquals(200, r.status());
        assertEquals("{\"result\":15}", r.json());
    }

    @Test
    void divideReturnsDecimal() {
        assertEquals("{\"result\":2.5}", handler.handle("divide", "10", "4").json());
    }

    @Test
    void divideByZeroIsBadRequest() {
        CalculationHandler.Response r = handler.handle("divide", "1", "0");
        assertEquals(400, r.status());
        assertTrue(r.json().contains("Cannot divide by zero"));
    }

    @Test
    void evenOddNeedsOnlyFirstNumber() {
        assertEquals("{\"result\":\"odd\"}", handler.handle("evenodd", "7", null).json());
    }

    @Test
    void missingOperation() {
        assertEquals(400, handler.handle(null, "1", "2").status());
    }

    @Test
    void unknownOperation() {
        assertEquals(400, handler.handle("power", "1", "2").status());
    }

    @Test
    void invalidNumber() {
        CalculationHandler.Response r = handler.handle("add", "abc", "2");
        assertEquals(400, r.status());
        assertTrue(r.json().contains("valid whole number"));
    }

    @Test
    void missingSecondNumber() {
        assertEquals(400, handler.handle("add", "1", "").status());
    }
}
