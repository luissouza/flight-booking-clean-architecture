package org.pt.flightbooking.adapters.datasources.repository;

import org.pt.flightbooking.adapters.dto.FlightLogs;
import java.util.List;
import java.util.Optional;

public interface FlightLogsRepository {

    List<FlightLogs> findAll();
    List<FlightLogs> findAll(int page, int rpp);
    Optional<FlightLogs> findById(Object id);
    void deleteAll();
    void deleteById(Object id);
    void create(FlightLogs record);

}
