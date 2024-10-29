package org.pt.flightbooking.adapters.schema.elastic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "es_flight_logs")
public class FlightLogsEs {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Keyword)
    private String flyTo;
    @Field(type = FieldType.Keyword)
    private String currency;
    @Field(type = FieldType.Keyword)
    private String dateTo;
    @Field(type = FieldType.Keyword)
    private String dateFrom;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recordDateTime;

    public FlightLogsEs(String flyTo, String currency, String dateTo, String dateFrom, LocalDateTime recordDateTime) {
        this.flyTo = flyTo;
        this.currency = currency;
        this.dateTo = dateTo;
        this.dateFrom = dateFrom;
        this.recordDateTime = recordDateTime;
    }
}