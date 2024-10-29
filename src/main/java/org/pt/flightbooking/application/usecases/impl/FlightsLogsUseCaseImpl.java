package org.pt.flightbooking.application.usecases.impl;

import java.util.List;
import java.util.Optional;

import org.pt.flightbooking.entities.model.FlightLogModel;
import org.pt.flightbooking.application.dto.response.FlightLogsOutputDto;
import org.pt.flightbooking.application.mappers.FlightLogsMapper;
import org.pt.flightbooking.application.usecases.FlightsLogsUseCase;
import org.pt.flightbooking.application.exception.DeleteFlightLogsException;
import org.pt.flightbooking.application.exception.FlightsDataNotFoundException;
import org.pt.flightbooking.entities.gateway.FlightsLogsGateway;
import org.pt.flightbooking.entities.gateway.LoggerGateway;

public class FlightsLogsUseCaseImpl implements FlightsLogsUseCase {

  private final FlightsLogsGateway flightsLogsGateway;
  private final LoggerGateway loggerGateway;

  public FlightsLogsUseCaseImpl(final FlightsLogsGateway repository, final LoggerGateway loggerGateway) {
    this.flightsLogsGateway = repository;
    this.loggerGateway = loggerGateway;
  }

  @Override
  public Optional<List<FlightLogsOutputDto>> filterFlightsLogs(int page, int rpp) {
    loggerGateway.info("FlightsRecordsService.filterFlightsLogs - Method Started...");
    Optional<List<FlightLogModel>> flightLogModel = Optional.ofNullable(this.flightsLogsGateway.findAll(page, rpp).orElseThrow(FlightsDataNotFoundException::new));
    return Optional.of(FlightLogsMapper.toListDto(flightLogModel.get()));
  }

  @Override
  public Optional<FlightLogsOutputDto> filterById(Integer id) {
    Optional<FlightLogModel> flightLogModel = Optional.ofNullable(this.flightsLogsGateway.findById(id).orElseThrow(FlightsDataNotFoundException::new));
    return Optional.of(FlightLogsMapper.toDto(flightLogModel.get()));
  }

  @Override
  public void deleteAll() {
    try {
      loggerGateway.info("FlightsRecordsService.deleteAll - Method Started...");
      this.flightsLogsGateway.deleteAll();
    } catch (final Exception e) {
      throw new DeleteFlightLogsException(e);
    }
  }

  @Override
  public boolean deleteById(Object id) {
    try {
      loggerGateway.info("FlightsRecordsService.deleteById - Method Started...");

      Optional<FlightLogModel> flightLogModel = this.flightsLogsGateway.findById(id);

      if(flightLogModel.isPresent()) {
        this.flightsLogsGateway.deleteById(id);
        return true;
      } else {
        return false;
      }

    } catch (final Exception e) {
      throw new DeleteFlightLogsException(e);
    }
  }

}
