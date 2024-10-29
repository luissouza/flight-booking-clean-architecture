package org.pt.flightbooking.adapters.exception.mappings;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "exceptions")
public class ExceptionMappingProperties {

  public ExceptionMappingProperties() {
  }

  private Map<String, ExceptionMappingPropertiesDetails> mappings = new HashMap();

  public Map<String, ExceptionMappingPropertiesDetails> getMappings() {
    return this.mappings;
  }

  public void setMappings(final Map<String, ExceptionMappingPropertiesDetails> mappings) {
    this.mappings = mappings;
  }

  public ExceptionMappingPropertiesDetails getExceptionMapping(final String code, final String originalMessage) {
    final String mapping = this.getMappings().keySet().stream()
      .filter(exceptionMappingPropertiesDetails ->
        this.getMappings().get(exceptionMappingPropertiesDetails).getOriginalMessage().equals(originalMessage) &&
          this.getMappings().get(exceptionMappingPropertiesDetails).getHttpCode() == Integer.parseInt(code))
      .findFirst()
      .orElse(null);
    return Optional.ofNullable(this.getMappings().get(mapping)).orElse(null);
  }
}
