package org.pt.flightbooking.adapters.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FlightDetailsWebResponse(String id,
                                       String flyFrom,
                                       String cityFrom,
                                       String cityCodeFrom,
                                       String flyTo,
                                       String cityTo,
                                       String cityCodeTo,
                                       Float price,
                                       @JsonProperty("dTime") Long dTime,
                                       @JsonProperty("dTimeFormatted") String dTimeFormatted,
                                       @JsonProperty("aTimeFormatted") String aTimeFormatted,
                                       @JsonProperty("dTimeUTC") Long dTimeUTC,
                                       @JsonProperty("aTime") Long aTime,
                                       @JsonProperty("aTimeUTC") Long aTimeUTC,
                                       @JsonProperty("bags_price") FlightBagPriceWebResponse baggage) implements Serializable {

}