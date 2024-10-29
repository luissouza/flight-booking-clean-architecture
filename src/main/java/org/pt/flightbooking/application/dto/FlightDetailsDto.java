package org.pt.flightbooking.application.dto;

import java.io.Serializable;

public record FlightDetailsDto(String id,
							   String flyFrom,
							   String cityFrom,
							   String cityCodeFrom,
							   String flyTo,
							   String cityTo,
							   String cityCodeTo,
							   Float price,
							   Long dTime,
							   String dTimeFormatted,
							   String aTimeFormatted,
							   Long dTimeUTC,
							   Long aTime,
							   Long aTimeUTC,
							   FlightBagPriceDto baggage) implements Serializable {

}
