package org.pt.flightbooking.application.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void replaceSpecialChars_nullInput_returnsEmpty() {
        assertEquals("", StringUtils.replaceSpecialChars(null));
    }

    @Test
    void replaceSpecialChars_emptyInput_returnsEmpty() {
        assertEquals("", StringUtils.replaceSpecialChars(""));
    }

    @Test
    void replaceSpecialChars_onlyAlphanumeric_kept() {
        assertEquals("Test123", StringUtils.replaceSpecialChars("Test123"));
    }

    @Test
    void replaceSpecialChars_specialChars_removed() {
        assertEquals("abc123", StringUtils.replaceSpecialChars("!a@b#c$1%2^3&"));
    }

}