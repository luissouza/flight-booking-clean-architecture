package org.pt.flightbooking.adapters.infrastructure.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfigure {
  @Bean
  WebClient webClient() {
    return WebClient.builder().codecs(configurer -> configurer
            .defaultCodecs()
            .maxInMemorySize(16 * 1024 * 1024)).build();
  }
}
