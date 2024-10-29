package org.pt.flightbooking.adapters.gateway;

import java.util.List;
import java.util.Optional;
import org.pt.flightbooking.adapters.datasources.FlightsLogsDataSource;
import org.pt.flightbooking.entities.gateway.FlightsLogsGateway;
import org.pt.flightbooking.adapters.mapper.FlightRecordEntityMapper;
import org.pt.flightbooking.entities.model.FlightLogModel;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static org.pt.flightbooking.adapters.mapper.FlightRecordEntityMapper.convertModelToEntity;
import static org.pt.flightbooking.adapters.mapper.FlightRecordEntityMapper.convertModelsToEntity;

@Component
public class FlightsLogsGatewayImpl implements FlightsLogsGateway {

  private final FlightsLogsDataSource flightsRecordsDataSource;

  public FlightsLogsGatewayImpl(final FlightsLogsDataSource flightsRecordsDataSource) {
    this.flightsRecordsDataSource = flightsRecordsDataSource;
  }

  @Override
  public Optional<List<FlightLogModel>> findAll(int page, int rpp) {
    return Optional.of(convertModelsToEntity(flightsRecordsDataSource.findAll(page, rpp)));
  }

  @Override
  public Optional<FlightLogModel> findById(Object id) {
    return convertModelToEntity(flightsRecordsDataSource.findById(id));
  }

  @Override
  public void deleteAll() {
    flightsRecordsDataSource.deleteAll();
  }

  @Override
  public void deleteById(Object id) {
    flightsRecordsDataSource.deleteById(id);
  }

  @Override
  public void create(final FlightLogModel record) {
    flightsRecordsDataSource.create(FlightRecordEntityMapper.convertEntityToModel(record));
  }
}
