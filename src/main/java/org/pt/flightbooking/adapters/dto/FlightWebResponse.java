package org.pt.flightbooking.adapters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public record FlightWebResponse(@JsonProperty("search_id") String searchId,
                                @JsonProperty("currency") String currency,
                                @JsonProperty("fx_rate") Float fxRate,
                                @JsonProperty("data") List<FlightDetailsWebResponse> data) implements Serializable {
}
