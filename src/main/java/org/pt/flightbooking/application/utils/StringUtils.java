package org.pt.flightbooking.application.utils;

public final class StringUtils {

    public static String replaceSpecialChars(final String value) {
        if (value == null)
            return "";
        final String pattern = "[^a-zA-Z0-9]";
        return value.replaceAll(pattern, "");
    }

}
