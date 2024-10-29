package org.pt.flightbooking.application.exception;

public class FlightsDataNotFoundException extends RuntimeException {
	public FlightsDataNotFoundException() {
		super();
	}
	public FlightsDataNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
	public FlightsDataNotFoundException(final String message) {
		super(message);
	}
	public FlightsDataNotFoundException(final Throwable cause) {
		super(cause);
	}

}