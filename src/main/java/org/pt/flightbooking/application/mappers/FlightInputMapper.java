package org.pt.flightbooking.application.mappers;

import org.pt.flightbooking.application.dto.request.FlightSearchInputDto;
import org.pt.flightbooking.entities.dto.FlightSearchInput;

public class FlightInputMapper {

    public static FlightSearchInput toDto(FlightSearchInputDto inputDto) {
        return new FlightSearchInput(inputDto.currency(), inputDto.dateFrom(), inputDto.dateTo(), inputDto.flyTo(), inputDto.flyFrom(), inputDto.airLines());
    }
}
