package org.pt.flightbooking.application.mappers;

import org.junit.jupiter.api.Test;
import org.pt.flightbooking.entities.model.FlightDetails;
import org.pt.flightbooking.entities.model.FlightBagPrice;
import org.pt.flightbooking.application.dto.FlightDetailsDto;
import org.pt.flightbooking.application.dto.FlightBagPriceDto;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FlightDetailsMapperTest {

    @Test
    void toDto_mapsEntityListToDtoList() {
        var bag    = new FlightBagPrice(5.0, 10.0);
        var entity = new FlightDetails(
            "id1", "FROM", "CityFrom", "CF",
            "TO",   "CityTo",   "CT",
             100.0f,    123456789L, "dFmt", "aFmt", 
             123456000L, 123457000L, 123458000L, bag
        );

        var dtos = FlightDetailsMapper.toDto(List.of(entity));
        assertEquals(1, dtos.size());

        var dto = dtos.get(0);
        assertEquals(entity.id(),               dto.id());
        assertEquals(entity.flyFrom(),          dto.flyFrom());
        assertEquals(entity.cityFrom(),         dto.cityFrom());
        assertEquals(entity.cityCodeFrom(),     dto.cityCodeFrom());
        assertEquals(entity.flyTo(),            dto.flyTo());
        assertEquals(entity.cityTo(),           dto.cityTo());
        assertEquals(entity.cityCodeTo(),       dto.cityCodeTo());
        assertEquals(entity.price(),            dto.price());
        assertEquals(entity.dTime(),            dto.dTime());
        assertEquals(entity.dTimeFormatted(),   dto.dTimeFormatted());
        assertEquals(entity.aTimeFormatted(),   dto.aTimeFormatted());
        assertEquals(entity.dTimeUTC(),         dto.dTimeUTC());
        assertEquals(entity.aTime(),            dto.aTime());
        assertEquals(entity.aTimeUTC(),         dto.aTimeUTC());
        assertEquals(new FlightBagPriceDto(
            bag.bagOnePrice(), bag.bagTwoPrice()
        ), dto.baggage());
    }

}