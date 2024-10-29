package org.pt.flightbooking.entities.gateway;

import org.pt.flightbooking.entities.dto.FlightSearchInput;
import org.pt.flightbooking.entities.model.FlightModel;
import org.pt.flightbooking.entities.model.LocationModel;

public interface FlightsGateway {
  FlightModel getFlights(FlightSearchInput input);
  LocationModel getLocation(String iata);
}
