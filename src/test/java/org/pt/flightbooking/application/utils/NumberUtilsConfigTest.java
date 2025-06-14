package org.pt.flightbooking.application.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberUtilsConfigTest {

    @Test
    void round_typicalDecimalValues_roundedToTwoPlaces() {
        assertEquals(123.46, NumberUtilsConfig.round(123.456), 1e-6);
        assertEquals(123.45, NumberUtilsConfig.round(123.454), 1e-6);
    }

    @Test
    void round_negativeValue_correctlyRounded() {
        assertEquals(-123.46, NumberUtilsConfig.round(-123.456), 1e-6);
    }

    @Test
    void round_infiniteValue_returnsZero() {
        assertEquals(0.00, NumberUtilsConfig.round(Double.POSITIVE_INFINITY), 1e-6);
        assertEquals(0.00, NumberUtilsConfig.round(Double.NEGATIVE_INFINITY), 1e-6);
    }

    @Test
    void round_NaN_returnsZero() {
        assertEquals(0.00, NumberUtilsConfig.round(Double.NaN), 1e-6);
    }

}