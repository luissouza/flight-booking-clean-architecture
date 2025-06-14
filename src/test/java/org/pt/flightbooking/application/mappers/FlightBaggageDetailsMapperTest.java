package org.pt.flightbooking.application.mappers;

import org.junit.jupiter.api.Test;
import org.pt.flightbooking.entities.model.FlightBagPrice;
import org.pt.flightbooking.application.dto.FlightBagPriceDto;
import static org.junit.jupiter.api.Assertions.*;

class FlightBaggageDetailsMapperTest {

    @Test
    void toDto_copiesBagPrices() {
        var entity = new FlightBagPrice(10.0, 20.0);
        var dto    = FlightBaggageDetailsMapper.toDto(entity);
        assertEquals(entity.bagOnePrice(), dto.bagOnePrice());
        assertEquals(entity.bagTwoPrice(), dto.bagTwoPrice());
    }

}