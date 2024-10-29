package org.pt.flightbooking.application.dto;

import java.io.Serializable;
import java.util.List;

public record LocationDto(List<FlightLocationDto> locations) implements Serializable {
}
