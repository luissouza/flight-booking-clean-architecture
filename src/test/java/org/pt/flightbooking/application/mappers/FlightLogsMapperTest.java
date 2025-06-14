package org.pt.flightbooking.application.mappers;

import org.junit.jupiter.api.Test;
import org.pt.flightbooking.entities.model.FlightLogModel;
import org.pt.flightbooking.application.dto.response.FlightLogsOutputDto;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FlightLogsMapperTest {

    @Test
    void toDto_mapsSingleModelToDto() {
        var dt    = LocalDateTime.of(2025, 6, 14, 10, 20, 30);
        var model = new FlightLogModel("LIS", "EUR", "2025-06-15", "2025-06-14", dt);
        var dto   = FlightLogsMapper.toDto(model);

        assertEquals("EUR", dto.currency());
        assertEquals("2025-06-14", dto.dateFrom());
        assertEquals("2025-06-15", dto.dateTo());
        assertEquals("LIS", dto.flyTo());
        assertEquals("2025-06-14 10:20:30", dto.recordDateTime());
    }

    @Test
    void toListDto_mapsListOfModelsToListOfDtos() {
        var dt    = LocalDateTime.of(2025, 6, 14, 10, 20, 30);
        var model = new FlightLogModel("LIS", "EUR", "2025-06-15", "2025-06-14", dt);
        var list  = FlightLogsMapper.toListDto(List.of(model));

        assertEquals(1, list.size());
        assertEquals(list.get(0), FlightLogsMapper.toDto(model));
    }

}