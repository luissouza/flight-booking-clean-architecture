package org.pt.flightbooking.adapters.mapper;

import org.pt.flightbooking.adapters.dto.FlightDetailsWebResponse;
import org.pt.flightbooking.entities.model.FlightDetails;

import java.util.ArrayList;
import java.util.List;

public class FlightDetailsMapperAdapterWebResponse {

    public static List<FlightDetails> toModel(List<FlightDetailsWebResponse> entities) {

        List<FlightDetails> flightDetails = new ArrayList<>();
        entities.forEach((model) -> {

            FlightDetails flightDetail = new FlightDetails(
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
                    FlightBaggageDetailsMapperWebResponse.toModel(model.baggage()));

            flightDetails.add(flightDetail);
        });
        return flightDetails;
    }
}
