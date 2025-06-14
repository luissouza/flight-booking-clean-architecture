package org.pt.flightbooking.application.utils;

import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class WebClientUtilsConfigTest {

    @Test
    void generateUrlParams_emptyMap_returnsQuestionMark() {
        assertEquals("?", WebClientUtilsConfig.generateUrlParams(Map.of()));
    }

    @Test
    void generateUrlParams_singleEntry_appendsKeyValueAndAmpersand() {
        assertEquals("?key=value&",
            WebClientUtilsConfig.generateUrlParams(Map.of("key", "value")));
    }

    @Test
    void generateUrlParams_multipleEntries_preservesInsertionOrder() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("a", 1);
        params.put("b", 2);
        assertEquals("?a=1&b=2&", WebClientUtilsConfig.generateUrlParams(params));
    }

}