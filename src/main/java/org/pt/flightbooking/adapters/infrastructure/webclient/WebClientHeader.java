package org.pt.flightbooking.adapters.infrastructure.webclient;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
public class WebClientHeader {

  public static Consumer<HttpHeaders> httpHeaders(final String headers) {

    final String[] headerProperties = headers.split(";");

    final Map<String, String> parameters = Arrays.stream(headerProperties)
        .map(s -> s.split("=", 2))
        .collect(Collectors.toMap(a -> a[0], a -> a.length > 1 ? a[1] : ""));

    return httpHeaders -> httpHeaders.setAll(parameters);
  }

}
