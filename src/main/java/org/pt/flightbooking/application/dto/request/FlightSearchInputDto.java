package org.pt.flightbooking.application.dto.request;

import java.io.Serializable;

public record FlightSearchInputDto(String currency,
								   String dateFrom,
								   String dateTo,
								   String flyTo,
								   String flyFrom,
								   String airLines) implements Serializable {
}
