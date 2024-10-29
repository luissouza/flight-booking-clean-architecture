package org.pt.flightbooking.adapters.exception.mappings;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ExceptionMappingPropertiesDetails {

  private int httpCode;
  private String code;
  private String originalMessage;
  private String message;
  private boolean appendOriginalMessage;

}
