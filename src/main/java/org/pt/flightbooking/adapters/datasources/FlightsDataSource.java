package org.pt.flightbooking.adapters.datasources;

import org.pt.flightbooking.entities.dto.FlightSearchInput;
import org.pt.flightbooking.entities.model.FlightModel;
import org.pt.flightbooking.entities.model.LocationModel;

public interface FlightsDataSource {

	FlightModel getFlights(FlightSearchInput params);

	LocationModel getLocation(String iata);

}

