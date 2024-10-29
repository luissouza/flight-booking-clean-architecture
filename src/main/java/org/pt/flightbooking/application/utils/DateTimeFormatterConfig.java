package org.pt.flightbooking.application.utils;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterConfig {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    public static String convertIsoFormat(final String dateIso) throws ParseException {
        final LocalDate date = LocalDate.parse(dateIso);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return date.format(formatter);
    }

    public static String toStringFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


}
