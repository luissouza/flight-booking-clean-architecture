package org.pt.flightbooking.adapters.infrastructure.webclient.rest;

import java.util.HashMap;
import java.util.Map;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
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
import org.springframework.http.HttpStatus;
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

  @CircuitBreaker(name = "getFlights", fallbackMethod = "fallbackGetFlights")
  @Retry(name = "getFlights")
  @TimeLimiter(name = "getFlights")
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

  public ResponseEntity<?> fallbackGetFlights(FlightSearchInput params, Throwable t) {
    // Aqui você pode logar o erro e retornar uma resposta default ou mensagem amigável
    System.err.println("Fallback triggered for getFlights due to: " + t.getMessage());

    Map<String, Object> error = new HashMap<>();
    error.put("message", "Serviço de voos temporariamente indisponível.");
    error.put("error", t.getClass().getSimpleName());

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
  }

  @CircuitBreaker(name = "getLocation", fallbackMethod = "fallbackGetLocation")
  @Retry(name = "getLocation")
  @TimeLimiter(name = "getLocation")
  public ResponseEntity<?> getLocation(final String iata) {
    final Map<String, Object> mapParams = new HashMap<>();
    mapParams.put("id", iata);

    return retrieveQueryParams(WebClientUtilsConfig.generateUrlParams(mapParams), SkyPicker.Endpoint.label, service, SkyPicker.Locations.label,
        LocationModel.class);

  }

  public ResponseEntity<?> fallbackGetLocation(String iata, Throwable t) {
    System.err.println("Fallback triggered for getLocation due to: " + t.getMessage());

    Map<String, Object> error = new HashMap<>();
    error.put("message", "Serviço de localização temporariamente indisponível.");
    error.put("input", iata);
    error.put("error", t.getClass().getSimpleName());

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
  }
}