package org.pt.flightbooking.adapters.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Autowired
    ApplicationConfigProperties applicationConfigProperties;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(applicationConfigProperties.getElasticSearchUrl())
                .build();
    }
}