package org.pt.flightbooking.adapters.infrastructure.configuration;

import org.pt.flightbooking.entities.gateway.FlightsGateway;
import org.pt.flightbooking.entities.gateway.FlightsLogsGateway;
import org.pt.flightbooking.application.usecases.FlightsLogsUseCase;
import org.pt.flightbooking.application.usecases.FlightUseCase;
import org.pt.flightbooking.application.usecases.impl.FlightsLogsUseCaseImpl;
import org.pt.flightbooking.application.usecases.impl.FlightsUseCaseImpl;
import org.pt.flightbooking.entities.gateway.LoggerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentConfiguration {

    @Autowired
    FlightsLogsGateway flightsRecordsRepository;

    @Autowired
    FlightsGateway flightsGateway;

    @Autowired
    LoggerGateway loggerGateway;

    @Bean
    public FlightUseCase flightsService() {
        return new FlightsUseCaseImpl(flightsGateway, flightsRecordsRepository, loggerGateway);
    }

    @Bean
    public FlightsLogsUseCase flightsRecordsService() {
        return new FlightsLogsUseCaseImpl(flightsRecordsRepository, loggerGateway);
    }
}
