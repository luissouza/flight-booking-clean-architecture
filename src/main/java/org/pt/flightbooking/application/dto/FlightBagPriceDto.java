package org.pt.flightbooking.application.dto;

import java.io.Serializable;

public record FlightBagPriceDto(Double bagOnePrice, Double bagTwoPrice) implements Serializable {

}
