package org.pt.flightbooking;

import org.mockito.Mockito;
import org.pt.flightbooking.adapters.datasources.repository.ElasticSearchRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class CucumberTestConfiguration {

    @Bean
    @Primary
    public ElasticSearchRepository elasticSearchRepository() {
        return Mockito.mock(ElasticSearchRepository.class);
    }
}