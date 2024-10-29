package org.pt.flightbooking.application.mappers;

import java.util.ArrayList;
import java.util.List;
import org.pt.flightbooking.application.dto.FlightLocationDto;
import org.pt.flightbooking.application.dto.LocationDto;
import org.pt.flightbooking.entities.model.LocationModel;

public class LocationMapper {

    public static LocationDto toDto(LocationModel model) {

        List<FlightLocationDto> locations = new ArrayList<>();

        model.locations().forEach((data) -> {
            locations.add(new FlightLocationDto(data.code()));
        });

        return new LocationDto(locations);
    }
}
