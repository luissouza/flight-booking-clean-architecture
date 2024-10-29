package org.pt.flightbooking.adapters.infrastructure.webclient.rest;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.pt.flightbooking.adapters.dto.FlightWebResponse;
import org.pt.flightbooking.adapters.infrastructure.webclient.WebClientProperties;
import org.pt.flightbooking.adapters.infrastructure.webclient.WebClientPropertiesService;
import org.pt.flightbooking.adapters.infrastructure.webclient.constant.SkyPicker;
import org.pt.flightbooking.adapters.infrastructure.webclient.WebClientHandler;
import org.pt.flightbooking.adapters.exception.error.ErrorBuilder;
import org.pt.flightbooking.adapters.exception.mappings.ExceptionMapper;
import org.pt.flightbooking.application.utils.WebClientUtilsConfig;
import org.pt.flightbooking.entities.dto.FlightSearchInput;
import org.pt.flightbooking.entities.model.LocationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class WebClientRequest extends WebClientHandler {

  private final WebClientPropertiesService service;
  private final WebClientProperties properties;

  public WebClientRequest(final WebClient webClient,
      final WebClientProperties properties,
      final ErrorBuilder errorBuilder,
      final ExceptionMapper exceptionMapper) {

    super(webClient, properties, errorBuilder, exceptionMapper);
    this.properties = properties;
    this.service = properties.getService(SkyPicker.Endpoint.label);
  }

  public ResponseEntity<?> getFlights(final FlightSearchInput params) {

    final Integer v = properties.getV(service, SkyPicker.Flights.label);

    final Map<String, Object> mapParams = new HashMap<>();
    mapParams.put("fly_from", params.flyFrom());
    mapParams.put("fly_to", params.flyTo());
    mapParams.put("v", v);
    mapParams.put("curr", params.currency());
    mapParams.put("date_from", params.dateFrom());
    mapParams.put("date_to", params.dateTo());
    mapParams.put("select_airlines", params.airLines());

    return retrieveQueryParams(WebClientUtilsConfig.generateUrlParams(mapParams), SkyPicker.Endpoint.label, service, SkyPicker.Flights.label, FlightWebResponse.class);

  }

  public ResponseEntity<?> getLocation(final String iata) {
    final Map<String, Object> mapParams = new HashMap<>();
    mapParams.put("id", iata);

    return retrieveQueryParams(WebClientUtilsConfig.generateUrlParams(mapParams), SkyPicker.Endpoint.label, service, SkyPicker.Locations.label,
        LocationModel.class);

  }
}