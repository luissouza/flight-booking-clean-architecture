package org.pt.flightbooking.entities.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public record FlightLogModel(
         Integer id,
         String flyTo,
         String currency,
         String dateTo,
         String dateFrom,
         LocalDateTime recordDateTime) implements Serializable {

    public FlightLogModel(String flyTo, String currency, String dateTo, String dateFrom, LocalDateTime recordDateTime) {
        this(null, flyTo, currency, dateTo, dateFrom, recordDateTime);
    }

    public FlightLogModel() {
        this(null, "", "", "", "", null);
    }
}

