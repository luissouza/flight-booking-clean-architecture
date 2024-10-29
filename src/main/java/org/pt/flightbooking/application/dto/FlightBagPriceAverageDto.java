package org.pt.flightbooking.application.dto;

import java.io.Serializable;

public record FlightBagPriceAverageDto(Double bagOneAveragePrice, Double bagTwoAveragePrice) implements Serializable {

}
