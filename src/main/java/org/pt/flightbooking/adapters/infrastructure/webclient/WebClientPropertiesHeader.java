package org.pt.flightbooking.adapters.infrastructure.webclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebClientPropertiesHeader {

  private String apikey;

}
