package org.pt.flightbooking.application.mappers;

import org.junit.jupiter.api.Test;
import org.pt.flightbooking.application.dto.request.FlightSearchInputDto;
import org.pt.flightbooking.entities.dto.FlightSearchInput;
import static org.junit.jupiter.api.Assertions.*;

class FlightInputMapperTest {

    @Test
    void toDto_allFieldsMappedCorrectly() {
        var dto = new FlightSearchInputDto(
            "EUR", "2025-06-14", "2025-06-15", "LIS", "OPO", "RYR"
        );
        var input = FlightInputMapper.toDto(dto);
        assertEquals(dto.currency(), input.currency());
        assertEquals(dto.dateFrom(), input.dateFrom());
        assertEquals(dto.dateTo(),   input.dateTo());
        assertEquals(dto.flyTo(),    input.flyTo());
        assertEquals(dto.flyFrom(),  input.flyFrom());
        assertEquals(dto.airLines(), input.airLines());
    }

}