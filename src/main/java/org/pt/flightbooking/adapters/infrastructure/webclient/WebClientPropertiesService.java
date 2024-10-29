package org.pt.flightbooking.adapters.infrastructure.webclient;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebClientPropertiesService {


  private String baseUrl;
  private String headers;
  private Map<String, WebClientPropertiesEndpoint> endpoints = new HashMap();

  public WebClientPropertiesService() {
  }

  public String getHeaders() {
    return headers;
  }

  public void setHeaders(final String headers) {
    this.headers = headers;
  }

  public String getBaseUrl() {
    return this.baseUrl;
  }

  public Map<String, WebClientPropertiesEndpoint> getEndpoints() {
    return this.endpoints;
  }



  public void setBaseUrl(final String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public void setEndpoints(final Map<String, WebClientPropertiesEndpoint> endpoints) {
    this.endpoints = endpoints;
  }


}
