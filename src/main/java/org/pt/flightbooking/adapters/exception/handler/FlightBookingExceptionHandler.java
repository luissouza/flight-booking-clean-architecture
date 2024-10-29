package org.pt.flightbooking.adapters.exception.handler;

import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.pt.flightbooking.adapters.exception.dto.ErrorResponseDto;
import org.pt.flightbooking.adapters.exception.error.ErrorBuilder;
import org.pt.flightbooking.adapters.exception.message.ErrorMessage;
import org.pt.flightbooking.application.exception.DeleteFlightLogsException;
import org.pt.flightbooking.application.exception.AverageFlightsException;
import org.pt.flightbooking.application.exception.FlightDestinyException;
import org.pt.flightbooking.application.exception.FlightsDataNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class FlightBookingExceptionHandler extends ResponseEntityExceptionHandler {

	private final ErrorBuilder errorBuilder;

	public FlightBookingExceptionHandler(final ErrorBuilder errorBuilder) {
		this.errorBuilder = errorBuilder;
	}

	@ExceptionHandler(AverageFlightsException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponseDto handleException(final AverageFlightsException exception, final HttpServletRequest request) {
		return errorBuilder.createError(ErrorMessage.exchangeRequestError(exception.getMessage()), exception.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FlightDestinyException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponseDto handleException(final FlightDestinyException exception, final HttpServletRequest request) {
		return errorBuilder.createError(ErrorMessage.exchangeRequestError(exception.getMessage()), exception.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DeleteFlightLogsException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponseDto handleException(final DeleteFlightLogsException exception, final HttpServletRequest request) {
		return errorBuilder.createError(ErrorMessage.exchangeRequestError(exception.getMessage()), exception.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FlightsDataNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponseDto handleException(final FlightsDataNotFoundException exception, final HttpServletRequest request) {
		return errorBuilder.createError(ErrorMessage.exchangeRequestError(exception.getMessage()), exception.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
