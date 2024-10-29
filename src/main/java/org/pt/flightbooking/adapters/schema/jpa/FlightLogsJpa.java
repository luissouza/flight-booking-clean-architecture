package org.pt.flightbooking.adapters.schema.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FLIGHT_LOGS", schema="public")
public class FlightLogsJpa {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "FLY_TO")
    private String flyTo;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "DATE_TO")
    private String dateTo;

    @Column(name = "DATE_FROM")
    private String dateFrom;

    @Column(name = "RECORD_DATE_TIME")
    private LocalDateTime recordDateTime;

    public FlightLogsJpa(String flyTo, String currency, String dateTo, String dateFrom, LocalDateTime recordDateTime) {
        this.flyTo = flyTo;
        this.currency = currency;
        this.dateTo = dateTo;
        this.dateFrom = dateFrom;
        this.recordDateTime = recordDateTime;
    }
}