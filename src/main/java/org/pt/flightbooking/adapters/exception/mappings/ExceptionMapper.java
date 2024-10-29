package org.pt.flightbooking.adapters.exception.mappings;

import org.pt.flightbooking.adapters.exception.mappings.constants.ExceptionMappings;
import org.pt.flightbooking.adapters.exception.dto.ErrorResponseDto;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExceptionMapper {

  protected final ExceptionMappingProperties exceptionMappingProperties;

  public ExceptionMapper(
    final ExceptionMappingProperties exceptionMappingProperties
  ) {
    this.exceptionMappingProperties = exceptionMappingProperties;
  }


  public ErrorResponseDto mapException(final ErrorResponseDto error) {
    final ExceptionMappingPropertiesDetails details;

    if (error.getDetail().contains(ExceptionMappings.ClientSystemNotFoundShort.label)) {
      details = exceptionMappingProperties.getExceptionMapping(String.valueOf(error.getHttpCode()),
        ExceptionMappings.ClientSystemNotFound.label);

      String originalMessage = details.getOriginalMessage();

      final Matcher matcher = Pattern.compile("\\[([^]]+)\\]").matcher(error.getDetail());

      if (matcher.find()) {
        originalMessage = originalMessage.replace("[client_system]", matcher.group());
      }

      error.setMessage(details.isAppendOriginalMessage() ? details.getMessage() + " - " + originalMessage
        : details.getMessage());

    } else {
      details = exceptionMappingProperties.getExceptionMapping(String.valueOf(error.getHttpCode()),
        error.getDetail());
    }
    if (Objects.isNull(details)) {
      return error;
    }
    error.setHttpCode(Integer.valueOf(details.getCode()));
    return error;
  }
}
