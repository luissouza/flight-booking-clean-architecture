package org.pt.flightbooking.application.dto;

import java.io.Serializable;
import java.util.Map;

public record FlightHeaderResponseDto(Map<String, FlightResumeDetailsDto> averageFlights,
                                      String dateFrom,
                                      String dateTo) implements Serializable {
}






