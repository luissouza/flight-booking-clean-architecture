package org.pt.flightbooking.entities.dto;

public record FlightSearchInput(
		 String currency,
		 String dateFrom,
		 String dateTo,
		 String flyTo,
		 String flyFrom,
		 String airLines)   {
}
