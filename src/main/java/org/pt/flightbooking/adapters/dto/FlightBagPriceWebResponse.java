package org.pt.flightbooking.adapters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record FlightBagPriceWebResponse(@JsonProperty("1") Double bagOnePrice, @JsonProperty("2") Double bagTwoPrice) implements Serializable {

}