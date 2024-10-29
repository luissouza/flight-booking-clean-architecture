package org.pt.flightbooking.adapters.datasources.impl;

import org.pt.flightbooking.adapters.dto.FlightWebResponse;
import org.pt.flightbooking.adapters.infrastructure.webclient.rest.WebClientRequest;
import org.pt.flightbooking.adapters.datasources.FlightsDataSource;
import org.pt.flightbooking.entities.dto.FlightSearchInput;
import org.pt.flightbooking.entities.model.FlightModel;
import org.pt.flightbooking.entities.model.LocationModel;
import org.springframework.stereotype.Component;
import static org.pt.flightbooking.adapters.mapper.FlightMapperAdapterWebResponse.toModel;

@Component
public class FlightsDataSourceImpl implements FlightsDataSource {

	final WebClientRequest webClientRequest;

	public FlightsDataSourceImpl(final WebClientRequest webClientRequest) {
		this.webClientRequest = webClientRequest;
	}

	@Override
	public FlightModel getFlights(FlightSearchInput params) {
		FlightWebResponse response = (FlightWebResponse) webClientRequest.getFlights(params).getBody();
		return toModel(response);
	}

	@Override
	public LocationModel getLocation(String iata) {
		return (LocationModel) webClientRequest.getLocation(iata).getBody();
	}
}
