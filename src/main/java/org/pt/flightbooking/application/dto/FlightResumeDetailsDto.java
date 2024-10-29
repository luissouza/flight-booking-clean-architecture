package org.pt.flightbooking.application.dto;

import java.io.Serializable;

public record FlightResumeDetailsDto(
		 String cityName,
		 String currency,
		 Double priceAverage,
		 FlightBagPriceAverageDto bagsPrice) implements Serializable {
}

