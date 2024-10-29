package org.pt.flightbooking.application.mappers;

import org.pt.flightbooking.application.dto.response.FlightLogsOutputDto;
import org.pt.flightbooking.entities.model.FlightLogModel;
import java.util.ArrayList;
import java.util.List;

public class FlightLogsMapper {

    public static FlightLogsOutputDto toDto(FlightLogModel entity) {
        return new FlightLogsOutputDto(entity);
    }
    public static List<FlightLogsOutputDto> toListDto(List<FlightLogModel> entities) {
        List<FlightLogsOutputDto> flightRecordDtos = new ArrayList<>();
        entities.forEach((entity) -> flightRecordDtos.add(new FlightLogsOutputDto(entity)));
        return flightRecordDtos;
    }
}
