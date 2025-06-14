package org.pt.flightbooking.adapters.controller;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.pt.flightbooking.application.usecases.FlightUseCase;
import org.pt.flightbooking.application.dto.request.FlightSearchInputDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class FlightsController extends BaseController {

	private final FlightUseCase flightsService;

    public FlightsController(final FlightUseCase flightsService) {
        this.flightsService = flightsService;
    }

    @Tag(name = "FlightsController")
    @Operation(summary = "Get flights average")
    @Timed(value = "flights.avg", description = "Time to find flights")
    @RequestMapping(value = "flights/avg", method = RequestMethod.GET, headers="Accept=application/json")
    public ResponseEntity<?> filterFlights(@PathParam("flyFrom") final String flyFrom,
                                           @PathParam("flyTo") final String flyTo,
                                           @PathParam("currency") final String currency,
                                           @PathParam("dateFrom") final String dateFrom,
                                           @PathParam("dateTo") final String dateTo,
                                           @PathParam("airLines") final String airLines) {

        return responseDefault.build(flightsService.filterFlights(new FlightSearchInputDto(currency, dateFrom, dateTo, flyTo, flyFrom, airLines)), timeStamp, HttpStatus.OK);

    }

}
