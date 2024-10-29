package org.pt.flightbooking.entities.model;

import java.io.Serializable;
import java.util.List;

public record LocationModel(List<FlightLocation> locations) implements Serializable {

}
