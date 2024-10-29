package org.pt.flightbooking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "FlightBooking API", version = "1.0", description = "FlightBooking API Test Documentation"))
public class FlightBookingApplication {

  public static void main(final String[] args) {
    SpringApplication.run(FlightBookingApplication.class, args);
  }

}
