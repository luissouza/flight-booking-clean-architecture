package org.pt.flightbooking.application.exception;

public class AverageFlightsException extends RuntimeException {
  public AverageFlightsException() {
    super();
  }
  public AverageFlightsException(final String message, final Throwable cause) {
    super(message, cause);
  }
  public AverageFlightsException(final String message) {
    super(message);
  }
  public AverageFlightsException(final Throwable cause) {
    super(cause);
  }

}
