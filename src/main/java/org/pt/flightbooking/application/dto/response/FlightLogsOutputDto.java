package org.pt.flightbooking.application.dto.response;

import org.pt.flightbooking.entities.model.FlightLogModel;

import java.io.Serializable;

import static org.pt.flightbooking.application.utils.DateTimeFormatterConfig.toStringFormat;

public record FlightLogsOutputDto(
  String currency,
  String dateFrom,
  String dateTo,
  String flyTo,
  String recordDateTime) implements Serializable {

  public FlightLogsOutputDto(FlightLogModel flightRecordModel) {
    this(
            flightRecordModel.currency(),
            flightRecordModel.dateFrom(),
            flightRecordModel.dateTo(),
            flightRecordModel.flyTo(),
            toStringFormat(flightRecordModel.recordDateTime())
    );
  }

}
