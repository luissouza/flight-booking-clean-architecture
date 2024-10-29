package org.pt.flightbooking.adapters.infrastructure.webclient;

import lombok.*;
import org.springframework.http.HttpMethod;
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebClientPropertiesEndpoint {

  private String path;
  private HttpMethod method;
  private int timeout = 20;
  private Integer v;

}
