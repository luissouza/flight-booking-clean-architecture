package org.pt.flightbooking;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = {FlightBookingApplication.class, CucumberTestConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@org.springframework.test.context.TestPropertySource(locations = "classpath:application-test.properties")
public class CucumberSpringConfiguration {
}