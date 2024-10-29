package org.pt.flightbooking.application.mappers;

import org.pt.flightbooking.application.dto.FlightBagPriceDto;
import org.pt.flightbooking.entities.model.FlightBagPrice;

public class FlightBaggageDetailsMapper {

    public static FlightBagPriceDto toDto(FlightBagPrice entity) {
        return new FlightBagPriceDto(entity.bagOnePrice(), entity.bagTwoPrice());
    }
}
