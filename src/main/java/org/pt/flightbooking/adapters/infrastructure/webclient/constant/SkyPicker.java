package org.pt.flightbooking.adapters.infrastructure.webclient.constant;

public enum SkyPicker {

  Endpoint("skyPicker"),
  Flights("filter"),
  Locations("locations");
  public final String label;

  SkyPicker(final String label) {
    this.label = label;
  }
}