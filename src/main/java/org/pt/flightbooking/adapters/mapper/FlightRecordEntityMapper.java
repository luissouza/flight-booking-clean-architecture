package org.pt.flightbooking.adapters.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.pt.flightbooking.adapters.dto.FlightLogs;
import org.pt.flightbooking.entities.model.FlightLogModel;

public interface FlightRecordEntityMapper {

  static FlightLogs convertEntityToModel(final FlightLogModel entity) {
      return FlightLogs.builder()
              .currency(entity.currency())
              .flyTo(entity.flyTo())
              .dateFrom(entity.dateFrom())
              .dateTo(entity.dateTo())
              .recordDateTime(entity.recordDateTime()).build();
  }

   static List<FlightLogModel> convertModelsToEntity(final List<FlightLogs> recordEntities) {
      List<FlightLogModel> flightRecordEntities = new ArrayList<>();
      recordEntities.forEach((entity) -> flightRecordEntities.add(new FlightLogModel(entity.getCurrency(), entity.getFlyTo(), entity.getDateFrom(), entity.getDateTo(), entity.getRecordDateTime())));
      return flightRecordEntities;
  }

    static Optional<FlightLogModel> convertModelToEntity(Optional<FlightLogs> entity) {
        if(entity.isPresent()) {
            return Optional.of(new FlightLogModel(entity.get().getCurrency(), entity.get().getFlyTo(), entity.get().getDateFrom(), entity.get().getDateTo(), entity.get().getRecordDateTime()));
        } else {
            return Optional.empty();
        }
    }

}
