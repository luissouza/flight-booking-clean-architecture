package org.pt.flightbooking.application.usecases.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pt.flightbooking.application.dto.FlightBagPriceAverageDto;
import org.pt.flightbooking.application.dto.FlightBagPriceDto;
import org.pt.flightbooking.application.dto.FlightDetailsDto;
import org.pt.flightbooking.application.dto.FlightHeaderResponseDto;
import org.pt.flightbooking.application.dto.FlightResumeDetailsDto;
import org.pt.flightbooking.application.dto.request.FlightSearchInputDto;
import org.pt.flightbooking.application.dto.response.FlightResponseDto;
import org.pt.flightbooking.application.exception.AverageFlightsException;
import org.pt.flightbooking.application.exception.FlightDestinyException;
import org.pt.flightbooking.entities.dto.FlightSearchInput;
import org.pt.flightbooking.entities.gateway.FlightsGateway;
import org.pt.flightbooking.entities.gateway.FlightsLogsGateway;
import org.pt.flightbooking.entities.gateway.LoggerGateway;
import org.pt.flightbooking.entities.model.FlightBagPrice;
import org.pt.flightbooking.entities.model.FlightDetails;
import org.pt.flightbooking.entities.model.FlightLocation;
import org.pt.flightbooking.entities.model.FlightLogModel;
import org.pt.flightbooking.entities.model.FlightModel;
import org.pt.flightbooking.entities.model.LocationModel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightsUseCaseImplTest {

    @Mock
    private FlightsGateway flightsGateway;

    @Mock
    private FlightsLogsGateway flightsLogsGateway;

    @Mock
    private LoggerGateway loggerGateway;

    private FlightsUseCaseImpl flightsUseCase;

    @BeforeEach
    void setUp() {
        flightsUseCase = new FlightsUseCaseImpl(flightsGateway, flightsLogsGateway, loggerGateway);
    }

    @Test
    void filterFlights_validInput_returnsFlightHeaderResponse() {
        // Arrange
        FlightSearchInputDto inputDto = new FlightSearchInputDto("EUR", "2025-06-14", "2025-06-21", "LIS,OPO", "LIS", "TAP");
        
        // Mock location validation
        LocationModel locationModel1 = new LocationModel(Arrays.asList(
            new FlightLocation("LIS")
        ));
        LocationModel locationModel2 = new LocationModel(Arrays.asList(
            new FlightLocation("OPO")
        ));
        
        when(flightsGateway.getLocation("LIS")).thenReturn(locationModel1);
        when(flightsGateway.getLocation("OPO")).thenReturn(locationModel2);
        
        // Mock flight data
        FlightModel flightModel = new FlightModel("search123", "EUR", 1.0f, Arrays.asList(
            createFlightDetailsModel("LIS", "Lisbon", 100.0f, 20.0, 30.0),
            createFlightDetailsModel("LIS", "Lisbon", 120.0f, 25.0, 35.0),
            createFlightDetailsModel("OPO", "Porto", 80.0f, 15.0, 25.0)
        ));
        
        when(flightsGateway.getFlights(any(FlightSearchInput.class))).thenReturn(flightModel);

        // Act
        FlightHeaderResponseDto result = flightsUseCase.filterFlights(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals("14/06/2025", result.dateFrom());
        assertEquals("21/06/2025", result.dateTo());
        assertEquals(2, result.averageFlights().size());
        
        // Verify LIS average
        FlightResumeDetailsDto lisResume = result.averageFlights().get("LIS");
        assertNotNull(lisResume);
        assertEquals("Lisbon", lisResume.cityName());
        assertEquals("EUR", lisResume.currency());
        assertEquals(110.0, lisResume.priceAverage()); // (100 + 120) / 2
        assertEquals(22.5, lisResume.bagsPrice().bagOneAveragePrice()); // (20 + 25) / 2
        assertEquals(32.5, lisResume.bagsPrice().bagTwoAveragePrice()); // (30 + 35) / 2
        
        // Verify OPO average
        FlightResumeDetailsDto opoResume = result.averageFlights().get("OPO");
        assertNotNull(opoResume);
        assertEquals("Porto", opoResume.cityName());
        assertEquals("EUR", opoResume.currency());
        assertEquals(80.0, opoResume.priceAverage());
        assertEquals(15.0, opoResume.bagsPrice().bagOneAveragePrice());
        assertEquals(25.0, opoResume.bagsPrice().bagTwoAveragePrice());

        // Verify interactions
        verify(flightsGateway).getLocation("LIS");
        verify(flightsGateway).getLocation("OPO");
        verify(flightsGateway).getFlights(any(FlightSearchInput.class));
        verify(flightsLogsGateway).create(any(FlightLogModel.class));
        verify(loggerGateway, atLeastOnce()).info(anyString(), any());
    }

    @Test
    void filterFlights_invalidAirportCodes_throwsAverageFlightsException() {
        // Arrange
        FlightSearchInputDto inputDto = new FlightSearchInputDto("EUR", "2025-06-14", "2025-06-21", "INVALID", "LIS", "TAP");

        // Act & Assert
        assertThrows(AverageFlightsException.class, () -> flightsUseCase.filterFlights(inputDto));
        
        verify(loggerGateway).info(anyString(), any());
    }

    @Test
    void filterFlights_exceptionDuringProcessing_throwsAverageFlightsException() {
        // Arrange
        FlightSearchInputDto inputDto = new FlightSearchInputDto("EUR", "2025-06-14", "2025-06-21", "LIS,OPO", "LIS", "TAP");
        
        when(flightsGateway.getLocation(anyString())).thenThrow(new RuntimeException("Gateway error"));

        // Act & Assert
        assertThrows(AverageFlightsException.class, () -> flightsUseCase.filterFlights(inputDto));
        
        verify(loggerGateway).info(anyString(), any());
    }

    @Test
    void validateAirports_validTwoAirportCodes_returnsLocations() {
        // Arrange
        FlightSearchInputDto inputDto = new FlightSearchInputDto("EUR", "2025-06-14", "2025-06-21", "LIS,OPO", "LIS", "TAP");
        
        LocationModel locationModel1 = new LocationModel(Arrays.asList(
            new FlightLocation("LIS")
        ));
        LocationModel locationModel2 = new LocationModel(Arrays.asList(
            new FlightLocation("OPO")
        ));
        
        when(flightsGateway.getLocation("LIS")).thenReturn(locationModel1);
        when(flightsGateway.getLocation("OPO")).thenReturn(locationModel2);

        // Act
        String result = flightsUseCase.validateAirports(inputDto);

        // Assert
        assertEquals("LIS,OPO", result);
        verify(flightsGateway).getLocation("LIS");
        verify(flightsGateway).getLocation("OPO");
    }

    @Test
    void validateAirports_singleAirportCode_throwsFlightDestinyException() {
        // Arrange
        FlightSearchInputDto inputDto = new FlightSearchInputDto("EUR", "2025-06-14", "2025-06-21", "LIS", "LIS", "TAP");

        // Act & Assert
        FlightDestinyException exception = assertThrows(FlightDestinyException.class, 
            () -> flightsUseCase.validateAirports(inputDto));
        
        assertTrue(exception.getMessage().contains("two airport codes"));
    }

    @Test
    void validateAirports_threeAirportCodes_throwsFlightDestinyException() {
        // Arrange
        FlightSearchInputDto inputDto = new FlightSearchInputDto("EUR", "2025-06-14", "2025-06-21", "LIS,OPO,MAD", "LIS", "TAP");

        // Act & Assert
        FlightDestinyException exception = assertThrows(FlightDestinyException.class, 
            () -> flightsUseCase.validateAirports(inputDto));
        
        assertTrue(exception.getMessage().contains("two airport codes"));
    }

    @Test
    void validateAirports_invalidFirstAirportCode_throwsFlightDestinyException() {
        // Arrange
        FlightSearchInputDto inputDto = new FlightSearchInputDto("EUR", "2025-06-14", "2025-06-21", "INVALID,OPO", "LIS", "TAP");
        
        LocationModel emptyLocationModel = new LocationModel(Arrays.asList());
        LocationModel validLocationModel = new LocationModel(Arrays.asList(
            new FlightLocation("OPO")
        ));
        
        when(flightsGateway.getLocation("INVALID")).thenReturn(emptyLocationModel);
        when(flightsGateway.getLocation("OPO")).thenReturn(validLocationModel);

        // Act & Assert
        FlightDestinyException exception = assertThrows(FlightDestinyException.class, 
            () -> flightsUseCase.validateAirports(inputDto));
        
        assertTrue(exception.getMessage().contains("invalid"));
    }

    @Test
    void validateAirports_invalidSecondAirportCode_throwsFlightDestinyException() {
        // Arrange
        FlightSearchInputDto inputDto = new FlightSearchInputDto("EUR", "2025-06-14", "2025-06-21", "LIS,INVALID", "LIS", "TAP");
        
        LocationModel validLocationModel = new LocationModel(Arrays.asList(
            new FlightLocation("LIS")
        ));
        LocationModel emptyLocationModel = new LocationModel(Arrays.asList());
        
        when(flightsGateway.getLocation("LIS")).thenReturn(validLocationModel);
        when(flightsGateway.getLocation("INVALID")).thenReturn(emptyLocationModel);

        // Act & Assert
        FlightDestinyException exception = assertThrows(FlightDestinyException.class, 
            () -> flightsUseCase.validateAirports(inputDto));
        
        assertTrue(exception.getMessage().contains("invalid"));
    }

    @Test
    void groupFlightsByDestiny_validFlights_groupsCorrectly() {
        // Arrange
        FlightResponseDto responseDto = new FlightResponseDto("search123", "EUR", 1.0f, Arrays.asList(
            createFlightDetailsDto("LIS", "Lisbon", 100.0f, 20.0, 30.0),
            createFlightDetailsDto("LIS", "Lisbon", 120.0f, 25.0, 35.0),
            createFlightDetailsDto("OPO", "Porto", 80.0f, 15.0, 25.0)
        ));

        // Act
        Map<String, List<FlightDetailsDto>> result = flightsUseCase.groupFlightsByDestiny(responseDto);

        // Assert
        assertEquals(2, result.size());
        assertEquals(2, result.get("LIS").size());
        assertEquals(1, result.get("OPO").size());
    }

    @Test
    void calcAvg_validFlights_calculatesCorrectAverages() {
        // Arrange
        Map<String, FlightResumeDetailsDto> result = new HashMap<>();
        List<FlightDetailsDto> flights = Arrays.asList(
            createFlightDetailsDto("LIS", "Lisbon", 100.0f, 20.0, 30.0),
            createFlightDetailsDto("LIS", "Lisbon", 120.0f, 25.0, 35.0)
        );
        FlightResponseDto flightsDto = new FlightResponseDto("search123", "EUR", 1.0f, flights);

        // Act
        flightsUseCase.calcAvg(result, "LIS", flights, flightsDto);

        // Assert
        assertEquals(1, result.size());
        FlightResumeDetailsDto resume = result.get("LIS");
        assertNotNull(resume);
        assertEquals("Lisbon", resume.cityName());
        assertEquals("EUR", resume.currency());
        assertEquals(110.0, resume.priceAverage()); // (100 + 120) / 2
        assertEquals(22.5, resume.bagsPrice().bagOneAveragePrice()); // (20 + 25) / 2
        assertEquals(32.5, resume.bagsPrice().bagTwoAveragePrice()); // (30 + 35) / 2
    }

    @Test
    void calcAvg_flightsWithNullBaggage_handlesNullValues() {
        // Arrange
        Map<String, FlightResumeDetailsDto> result = new HashMap<>();
        List<FlightDetailsDto> flights = Arrays.asList(
            createFlightDetailsDto("LIS", "Lisbon", 100.0f, null, null),
            createFlightDetailsDto("LIS", "Lisbon", 120.0f, 25.0, 35.0)
        );
        FlightResponseDto flightsDto = new FlightResponseDto("search123", "EUR", 1.0f, flights);

        // Act
        flightsUseCase.calcAvg(result, "LIS", flights, flightsDto);

        // Assert
        assertEquals(1, result.size());
        FlightResumeDetailsDto resume = result.get("LIS");
        assertNotNull(resume);
        assertEquals(110.0, resume.priceAverage()); // (100 + 120) / 2
        assertEquals(12.5, resume.bagsPrice().bagOneAveragePrice()); // (0 + 25) / 2
        assertEquals(17.5, resume.bagsPrice().bagTwoAveragePrice()); // (0 + 35) / 2
    }

    @Test
    void saveRecord_validInput_savesFlightLogModel() {
        // Arrange
        FlightSearchInputDto inputDto = new FlightSearchInputDto("EUR", "2025-06-14", "2025-06-21", "LIS,OPO", "LIS", "TAP");

        // Act
        flightsUseCase.saveRecord(inputDto);

        // Assert
        verify(flightsLogsGateway).create(argThat(flightLog -> 
            flightLog.flyTo().equals("LIS,OPO") &&
            flightLog.currency().equals("EUR") &&
            flightLog.dateTo().equals("2025-06-21") &&
            flightLog.dateFrom().equals("2025-06-14") &&
            flightLog.recordDateTime() != null
        ));
    }

    @Test
    void getFlights_validInput_returnsFlightResponse() {
        // Arrange
        FlightSearchInputDto inputDto = new FlightSearchInputDto("EUR", "2025-06-14", "2025-06-21", "LIS,OPO", "LIS", "TAP");
        FlightModel flightModel = new FlightModel("search123", "EUR", 1.0f, Arrays.asList(
            createFlightDetailsModel("LIS", "Lisbon", 100.0f, 20.0, 30.0)
        ));
        
        when(flightsGateway.getFlights(any(FlightSearchInput.class))).thenReturn(flightModel);

        // Act
        FlightResponseDto result = flightsUseCase.getFlights(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals("EUR", result.currency());
        assertEquals(1, result.data().size());
        verify(flightsGateway).getFlights(any(FlightSearchInput.class));
        verify(loggerGateway).info(anyString(), any());
    }

    private FlightDetailsDto createFlightDetailsDto(String flyTo, String cityTo, Float price, Double bagOnePrice, Double bagTwoPrice) {
        FlightBagPriceDto baggage = new FlightBagPriceDto(bagOnePrice, bagTwoPrice);
        return new FlightDetailsDto("1", "LIS", "Lisbon", "LIS", flyTo, cityTo, flyTo, price, 
                                   System.currentTimeMillis(), "10:00", "12:00", 
                                   System.currentTimeMillis(), System.currentTimeMillis(), 
                                   System.currentTimeMillis(), baggage);
    }

    private FlightDetails createFlightDetailsModel(String flyTo, String cityTo, Float price, Double bagOnePrice, Double bagTwoPrice) {
        FlightBagPrice baggage = new FlightBagPrice(bagOnePrice, bagTwoPrice);
        return new FlightDetails("1", "LIS", "Lisbon", "LIS", flyTo, cityTo, flyTo, price, 
                                System.currentTimeMillis(), "10:00", "12:00", 
                                System.currentTimeMillis(), System.currentTimeMillis(), 
                                System.currentTimeMillis(), baggage);
    }
}