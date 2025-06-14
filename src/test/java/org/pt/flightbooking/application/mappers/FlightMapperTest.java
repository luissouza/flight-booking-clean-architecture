package org.pt.flightbooking.application.mappers;

import org.junit.jupiter.api.Test;
import org.pt.flightbooking.entities.model.FlightModel;
import org.pt.flightbooking.entities.model.FlightDetails;
import org.pt.flightbooking.entities.model.FlightBagPrice;
import org.pt.flightbooking.application.dto.response.FlightResponseDto;
import org.pt.flightbooking.application.dto.FlightDetailsDto;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FlightMapperTest {

    @Test
    void toDto_mapsFlightModelToResponseDto() {
        var bag    = new FlightBagPrice(5.0, 10.0);
        var detail = new FlightDetails(
            "id1", "FROM", "CityFrom", "CF",
            "TO",   "CityTo",   "CT",
             100.0f,    123456789L, "dFmt", "aFmt", 
             123456000L, 123457000L, 123458000L, bag
        );
        var model = new FlightModel("SID123", "EUR", 1.23f, List.of(detail));

        var dto = FlightMapper.toDto(model);
        assertEquals(model.searchId(), dto.searchId());
        assertEquals(model.currency(), dto.currency());
        assertEquals(model.fxRate(),   dto.fxRate());
        assertEquals(1,                dto.data().size());

        FlightDetailsDto mappedDetail = dto.data().get(0);
        assertEquals(detail.id(), mappedDetail.id());
    }

}