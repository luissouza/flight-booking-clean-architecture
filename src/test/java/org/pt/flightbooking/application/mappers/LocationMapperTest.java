package org.pt.flightbooking.application.mappers;

import org.junit.jupiter.api.Test;
import org.pt.flightbooking.entities.model.FlightLocation;
import org.pt.flightbooking.entities.model.LocationModel;
import org.pt.flightbooking.application.dto.FlightLocationDto;
import org.pt.flightbooking.application.dto.LocationDto;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LocationMapperTest {

    @Test
    void toDto_transformsModelListToDtoList() {
        var loc1 = new FlightLocation("LIS");
        var loc2 = new FlightLocation("OPO");
        var model = new LocationModel(List.of(loc1, loc2));
        var dto   = LocationMapper.toDto(model);

        assertEquals(2, dto.locations().size());
        assertEquals("LIS", dto.locations().get(0).code());
        assertEquals("OPO", dto.locations().get(1).code());
    }

}