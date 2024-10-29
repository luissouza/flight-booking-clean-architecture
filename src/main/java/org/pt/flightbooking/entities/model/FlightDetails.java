package org.pt.flightbooking.entities.model;

import java.io.Serializable;

public record FlightDetails(String id,
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
							   FlightBagPrice baggage) implements Serializable {

}


