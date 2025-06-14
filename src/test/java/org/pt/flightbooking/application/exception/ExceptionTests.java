package org.pt.flightbooking.application.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionTests {

    @Test
    void averageFlightsException_defaultConstructor_messageNull() {
        var ex = new AverageFlightsException();
        assertNull(ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void averageFlightsException_messageAndCause() {
        var cause = new RuntimeException("cause");
        var ex = new AverageFlightsException("error occurred", cause);
        assertEquals("error occurred", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void flightsDataNotFoundException_allConstructors() {
        var ex1 = new FlightsDataNotFoundException();
        assertNull(ex1.getMessage());

        var cause = new IllegalStateException();
        var ex2 = new FlightsDataNotFoundException("not found", cause);
        assertEquals("not found", ex2.getMessage());
        assertEquals(cause, ex2.getCause());

        var ex3 = new FlightsDataNotFoundException("just message");
        assertEquals("just message", ex3.getMessage());

        var ex4 = new FlightsDataNotFoundException(cause);
        assertEquals(cause, ex4.getCause());
    }

    @Test
    void deleteFlightLogsException_allConstructors() {
        var ex1 = new DeleteFlightLogsException();
        assertNull(ex1.getMessage());

        var cause = new Exception("boom");
        var ex2 = new DeleteFlightLogsException("delete failed", cause);
        assertEquals("delete failed", ex2.getMessage());
        assertEquals(cause, ex2.getCause());

        var ex3 = new DeleteFlightLogsException("just msg");
        assertEquals("just msg", ex3.getMessage());

        var ex4 = new DeleteFlightLogsException(cause);
        assertEquals(cause, ex4.getCause());
    }

    @Test
    void flightDestinyException_allConstructors() {
        var ex1 = new FlightDestinyException();
        assertNull(ex1.getMessage());

        var cause = new Throwable("x");
        var ex2 = new FlightDestinyException("destiny err", cause);
        assertEquals("destiny err", ex2.getMessage());
        assertEquals(cause, ex2.getCause());

        var ex3 = new FlightDestinyException("only msg");
        assertEquals("only msg", ex3.getMessage());

        var ex4 = new FlightDestinyException(cause);
        assertEquals(cause, ex4.getCause());
    }

}