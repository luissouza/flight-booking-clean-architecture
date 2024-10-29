package org.pt.flightbooking.adapters.datasources;

import org.pt.flightbooking.adapters.dto.FlightLogs;


import java.util.List;
import java.util.Optional;

public interface FlightsLogsDataSource {

	 List<FlightLogs> findAll(int page, int rpp);
	 Optional<FlightLogs> findById(Object id);
	 void deleteAll();
	 void deleteById(Object id);
	 void create(FlightLogs record);

}

