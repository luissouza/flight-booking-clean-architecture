package org.pt.flightbooking.entities.model;

import java.io.Serializable;
import java.util.List;

public record FlightModel(String searchId,
								String currency,
								Float fxRate,
								List<FlightDetails> data) implements Serializable {
}
