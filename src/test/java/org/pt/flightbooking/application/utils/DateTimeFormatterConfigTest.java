package org.pt.flightbooking.application.utils;

import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import static org.junit.jupiter.api.Assertions.*;

class DateTimeFormatterConfigTest {

    @Test
    void convertIsoFormat_validIsoDate_returnsFormatted() throws ParseException {
        assertEquals("14/06/2025", DateTimeFormatterConfig.convertIsoFormat("2025-06-14"));
    }

    @Test
    void convertIsoFormat_invalidIsoDate_throwsParseException() {
        assertThrows(DateTimeParseException.class,
            () -> DateTimeFormatterConfig.convertIsoFormat("not-a-date"));
    }

    @Test
    void toStringFormat_localDateTime_returnsCorrectPattern() {
        LocalDateTime dt = LocalDateTime.of(2025, 6, 14, 13, 45, 30);
        assertEquals("2025-06-14 13:45:30", DateTimeFormatterConfig.toStringFormat(dt));
    }

}