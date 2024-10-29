package org.pt.flightbooking.application.mappers;

import org.pt.flightbooking.application.dto.response.FlightResponseDto;
import org.pt.flightbooking.entities.model.FlightModel;

public class FlightMapper {

    public static FlightResponseDto toDto(FlightModel entity) {
        return new FlightResponseDto(entity.searchId(), entity.currency(), entity.fxRate(), FlightDetailsMapper.toDto(entity.data()));
    }
}
