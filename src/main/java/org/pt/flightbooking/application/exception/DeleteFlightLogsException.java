package org.pt.flightbooking.application.exception;

public class DeleteFlightLogsException extends RuntimeException {
  public DeleteFlightLogsException() {
    super();
  }
  public DeleteFlightLogsException(final String message, final Throwable cause) {
    super(message, cause);
  }
  public DeleteFlightLogsException(final String message) {
    super(message);
  }
  public DeleteFlightLogsException(final Throwable cause) {
    super(cause);
  }

}