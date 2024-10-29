package org.pt.flightbooking.adapters.datasources.impl;

import org.pt.flightbooking.adapters.datasources.FlightsLogsDataSource;
import org.pt.flightbooking.adapters.datasources.repository.FlightLogsRepository;
import org.pt.flightbooking.adapters.dto.FlightLogs;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class FlightsLogsDataSourceImpl implements FlightsLogsDataSource {

	final FlightLogsRepository flightLogsRepository;

	public FlightsLogsDataSourceImpl(final FlightLogsRepository flightLogsRepository) {
		this.flightLogsRepository = flightLogsRepository;
	}

	@Override
	public List<FlightLogs> findAll(int page, int rpp) {
		return flightLogsRepository.findAll(page, rpp);
	}

	@Override
	public Optional<FlightLogs> findById(Object id) {
		return this.flightLogsRepository.findById(id);
	}

	@Override
	public void deleteAll() {
		this.flightLogsRepository.deleteAll();
	}

	@Override
	public void deleteById(Object id) {
		this.flightLogsRepository.deleteById(id);
	}

	@Override
	public void create(final FlightLogs record) {
		this.flightLogsRepository.create(record);
	}
}
