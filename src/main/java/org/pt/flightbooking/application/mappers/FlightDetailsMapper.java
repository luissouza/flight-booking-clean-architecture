package org.pt.flightbooking.application.mappers;

import org.pt.flightbooking.application.dto.FlightDetailsDto;
import org.pt.flightbooking.entities.model.FlightDetails;
import java.util.ArrayList;
import java.util.List;

public class FlightDetailsMapper {

    public static List<FlightDetailsDto> toDto(List<FlightDetails> entities) {

        List<FlightDetailsDto> flightDetails = new ArrayList<>();
        entities.forEach((model) -> {

            FlightDetailsDto flightDetail = new FlightDetailsDto(
                    model.id(),
                    model.flyFrom(),
                    model.cityFrom(),
                    model.cityCodeFrom(),
                    model.flyTo(),
                    model.cityTo(),
                    model.cityCodeTo(),
                    model.price(),
                    model.dTime(),
                    model.dTimeFormatted(),
                    model.aTimeFormatted(),
                    model.dTimeUTC(),
                    model.aTime(),
                    model.aTimeUTC(),
                    FlightBaggageDetailsMapper.toDto(model.baggage()));

            flightDetails.add(flightDetail);
        });
        return flightDetails;
    }
}
