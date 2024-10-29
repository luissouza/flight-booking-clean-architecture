package org.pt.flightbooking.application.usecases;

import org.pt.flightbooking.application.dto.response.FlightLogsOutputDto;
import java.util.List;
import java.util.Optional;

public interface FlightsLogsUseCase {
    Optional<List<FlightLogsOutputDto>> filterFlightsLogs(int page, int rpp);
    Optional<FlightLogsOutputDto> filterById(Integer id);
    void deleteAll();
    boolean deleteById(Object id);
}
