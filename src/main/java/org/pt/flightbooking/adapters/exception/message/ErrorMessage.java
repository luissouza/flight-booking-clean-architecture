package org.pt.flightbooking.adapters.exception.message;

import org.springframework.stereotype.Component;

@Component
public class ErrorMessage {

  public static String exchangeRequestError(final String path) {
    return String.format("%s.", path);
  }

  public static String exchangeRequestError(final String path, final String errorReason) {
    return String.format("%s, %s.", path, errorReason);
  }

}


