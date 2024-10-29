package org.pt.flightbooking.entities.gateway;

import java.util.List;
import java.util.Optional;
import org.pt.flightbooking.entities.model.FlightLogModel;

public interface FlightsLogsGateway {
  Optional<List<FlightLogModel>> findAll(int page, int rpp);
  Optional<FlightLogModel> findById(Object id);
  void deleteAll();
  void deleteById(Object id);
  void create(FlightLogModel record);
}
