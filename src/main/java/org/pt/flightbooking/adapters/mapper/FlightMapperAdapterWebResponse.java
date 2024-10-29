package org.pt.flightbooking.adapters.mapper;

import org.pt.flightbooking.adapters.dto.FlightWebResponse;
import org.pt.flightbooking.entities.model.FlightModel;

public class FlightMapperAdapterWebResponse {

    public static FlightModel toModel(FlightWebResponse response) {
        return new FlightModel(response.searchId(), response.currency(), response.fxRate(), FlightDetailsMapperAdapterWebResponse.toModel(response.data()));
    }
}
