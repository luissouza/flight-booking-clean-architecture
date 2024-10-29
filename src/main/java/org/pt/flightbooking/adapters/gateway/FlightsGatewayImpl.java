package org.pt.flightbooking.adapters.gateway;

import org.pt.flightbooking.entities.dto.FlightSearchInput;
import org.pt.flightbooking.entities.gateway.FlightsGateway;
import org.pt.flightbooking.entities.model.FlightModel;
import org.pt.flightbooking.entities.model.LocationModel;

import org.pt.flightbooking.adapters.datasources.FlightsDataSource;

import org.springframework.stereotype.Component;

@Component
public class FlightsGatewayImpl implements FlightsGateway {

  private final FlightsDataSource flightsDataSource;

  private FlightsGatewayImpl(final FlightsDataSource flightsDataSource) {
    this.flightsDataSource = flightsDataSource;
  }

  @Override
  public FlightModel getFlights(FlightSearchInput input) {
    return flightsDataSource.getFlights(input);
  }

  @Override
  public LocationModel getLocation(String iata) {
    return flightsDataSource.getLocation(iata);
  }

}
