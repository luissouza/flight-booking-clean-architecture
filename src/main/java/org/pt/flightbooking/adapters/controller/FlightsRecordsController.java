package org.pt.flightbooking.adapters.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pt.flightbooking.application.usecases.FlightsLogsUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class FlightsRecordsController extends BaseController {

	private final FlightsLogsUseCase flightsRecordsService;

    public FlightsRecordsController(final FlightsLogsUseCase flightsRecordsService) {
        this.flightsRecordsService = flightsRecordsService;
    }

    @Tag(name = "FlightsRecordsController")
    @Operation(summary = "Get all flight records")
    @RequestMapping(value = "flight/records", method = RequestMethod.GET, headers="Accept=application/json")
    public ResponseEntity<?> filterFlightsLogs(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                      @RequestParam(name = "rpp", defaultValue = "1") int rpp) {
        return responseDefault.build(flightsRecordsService.filterFlightsLogs(page, rpp).get(), timeStamp, HttpStatus.OK);
    }

    @Tag(name = "FlightsRecordsController")
    @Operation(summary = "Delete all flight records")
    @RequestMapping(value = "flight/records/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable("id") Object id) {
        boolean isDeleted = flightsRecordsService.deleteById(id);
        if(isDeleted)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Tag(name = "FlightsRecordsController")
    @Operation(summary = "Delete all flight records")
    @RequestMapping(value = "flight/records", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        flightsRecordsService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
