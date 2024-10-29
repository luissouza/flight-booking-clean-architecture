package org.pt.flightbooking.adapters.exception.error;

import lombok.Data;
import org.pt.flightbooking.adapters.exception.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Data
public class ErrorBuilder {

  public ErrorResponseDto createError(final String message, final String exception, final HttpStatus status) {
    return ErrorResponseDto.builder()
      .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
      .httpCode(status.value())
      .message(status.getReasonPhrase())
      .exception(exception)
      .detail(message)
      .build();
  }
}
