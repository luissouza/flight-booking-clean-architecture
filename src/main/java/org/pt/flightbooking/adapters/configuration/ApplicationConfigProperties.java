package org.pt.flightbooking.adapters.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "database")
@Getter
public class ApplicationConfigProperties {

    @Value("${database.default}")
    private String databaseDefault;

    @Value("${elasticsearch.url}")
    private String elasticSearchUrl;


}