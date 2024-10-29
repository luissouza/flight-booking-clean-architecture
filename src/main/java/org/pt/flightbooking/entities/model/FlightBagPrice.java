package org.pt.flightbooking.entities.model;

import java.io.Serializable;

public record FlightBagPrice(Double bagOnePrice, Double bagTwoPrice) implements Serializable {

}