package org.pt.flightbooking.application.exception;

  public class FlightDestinyException extends RuntimeException {
    public FlightDestinyException() {
      super();
    }
    public FlightDestinyException(final String message, final Throwable cause) {
      super(message, cause);
    }
    public FlightDestinyException(final String message) {
      super(message);
    }
    public FlightDestinyException(final Throwable cause) {
      super(cause);
    }

  }