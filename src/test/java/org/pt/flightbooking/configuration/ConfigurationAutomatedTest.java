package org.pt.flightbooking.configuration;

import org.pt.flightbooking.FlightBookingApplication;
import org.pt.flightbooking.application.usecases.FlightUseCase;
import org.pt.flightbooking.application.usecases.FlightsLogsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest(classes = FlightBookingApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@CucumberContextConfiguration
public abstract class ConfigurationAutomatedTest {

    @Autowired
    public FlightUseCase flightsService;

    @Autowired
    public FlightsLogsUseCase flightsLogsUseCase;

}
