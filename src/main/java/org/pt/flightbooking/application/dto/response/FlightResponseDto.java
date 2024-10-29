package org.pt.flightbooking.application.dto.response;

import java.io.Serializable;
import org.pt.flightbooking.application.dto.FlightDetailsDto;
import java.util.List;

public record FlightResponseDto(String searchId,
								String currency,
								Float fxRate,
								List<FlightDetailsDto> data) implements Serializable {

}
