package org.pt.flightbooking.adapters.infrastructure.webclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "api")
public class WebClientProperties {

  public WebClientProperties() {
  }

  private Map<String, WebClientPropertiesService> services = new HashMap<>();

  public Map<String, WebClientPropertiesService> getServices() {
    return this.services;
  }
  public void setServices(final Map<String, WebClientPropertiesService> services) {
    this.services = services;
  }

  public WebClientPropertiesService getService(final String name) {
    final WebClientPropertiesService service = this.getServices().get(name);
    return Optional.ofNullable(service).orElseThrow(() -> new NullPointerException(String.format("Endpoint %s cannot " +  "be null", name)));
  }

  public String getBaseUrl(final String name) {
    final String baseUrl = this.getService(name).getBaseUrl();
    return Optional.ofNullable(baseUrl).orElseThrow(() -> new NullPointerException(String.format("%s baseUrl cannot " + "be null", name)));
  }

  public String getHeaders(final String name) {
    final String headers = this.getService(name).getHeaders();
    return Optional.ofNullable(headers).orElseThrow(() -> new NullPointerException(String.format("%s headers cannot " + "be null", name)));
  }

  public String getPath(final WebClientPropertiesService service, final String name) {
    final String path = service.getEndpoints().get(name).getPath();
    return Optional.ofNullable(path).orElseThrow(() -> new NullPointerException(String.format("%s path cannot " +  "be null", name)));
  }

  public HttpMethod getMethod(final WebClientPropertiesService service, final String name) {
    final HttpMethod method = service.getEndpoints().get(name).getMethod();
    return Optional.ofNullable(method).orElseThrow(() -> new NullPointerException(String.format("%s method cannot " +  "be null", name)));
  }

  public Integer getTimeout(final WebClientPropertiesService service, final String name) {
    return service.getEndpoints().get(name).getTimeout();
  }

  public Integer getV(final WebClientPropertiesService service, final String name) {
    final Integer v = service.getEndpoints().get(name).getV();
    return Optional.ofNullable(v).orElseThrow(() -> new NullPointerException(String.format("%s v cannot " +  "be null", name)));
  }

}
