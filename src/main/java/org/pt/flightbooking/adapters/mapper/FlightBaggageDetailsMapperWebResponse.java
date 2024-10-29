package org.pt.flightbooking.adapters.mapper;

import org.pt.flightbooking.adapters.dto.FlightBagPriceWebResponse;
import org.pt.flightbooking.entities.model.FlightBagPrice;

public class FlightBaggageDetailsMapperWebResponse {

    public static FlightBagPrice toModel(FlightBagPriceWebResponse entity) {
        return new FlightBagPrice(entity.bagOnePrice(), entity.bagTwoPrice());
    }
}
