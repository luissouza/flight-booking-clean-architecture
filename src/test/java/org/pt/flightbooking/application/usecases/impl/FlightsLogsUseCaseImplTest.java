package org.pt.flightbooking.application.usecases.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pt.flightbooking.application.dto.response.FlightLogsOutputDto;
import org.pt.flightbooking.application.exception.DeleteFlightLogsException;
import org.pt.flightbooking.application.exception.FlightsDataNotFoundException;
import org.pt.flightbooking.entities.gateway.FlightsLogsGateway;
import org.pt.flightbooking.entities.gateway.LoggerGateway;
import org.pt.flightbooking.entities.model.FlightLogModel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightsLogsUseCaseImplTest {

    @Mock
    private FlightsLogsGateway flightsLogsGateway;

    @Mock
    private LoggerGateway loggerGateway;

    private FlightsLogsUseCaseImpl flightsLogsUseCase;

    @BeforeEach
    void setUp() {
        flightsLogsUseCase = new FlightsLogsUseCaseImpl(flightsLogsGateway, loggerGateway);
    }

    @Test
    void filterFlightsLogs_validPagination_returnsFlightLogs() {
        // Arrange
        int page = 0;
        int rpp = 10;
        
        List<FlightLogModel> flightLogs = Arrays.asList(
            createFlightLogModel(1, "LIS,OPO", "EUR", "2025-06-14", "2025-06-21"),
            createFlightLogModel(2, "MAD,BCN", "EUR", "2025-07-01", "2025-07-08")
        );
        
        when(flightsLogsGateway.findAll(page, rpp)).thenReturn(Optional.of(flightLogs));

        // Act
        Optional<List<FlightLogsOutputDto>> result = flightsLogsUseCase.filterFlightsLogs(page, rpp);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        
        FlightLogsOutputDto firstLog = result.get().get(0);
        assertEquals("LIS,OPO", firstLog.flyTo());
        assertEquals("EUR", firstLog.currency());
        assertEquals("2025-06-14", firstLog.dateFrom());
        assertEquals("2025-06-21", firstLog.dateTo());
        assertNotNull(firstLog.recordDateTime());
        
        verify(flightsLogsGateway).findAll(page, rpp);
        verify(loggerGateway).info(anyString());
    }

    @Test
    void filterFlightsLogs_noDataFound_throwsFlightsDataNotFoundException() {
        // Arrange
        int page = 0;
        int rpp = 10;
        
        when(flightsLogsGateway.findAll(page, rpp)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FlightsDataNotFoundException.class, 
            () -> flightsLogsUseCase.filterFlightsLogs(page, rpp));
        
        verify(flightsLogsGateway).findAll(page, rpp);
        verify(loggerGateway).info(anyString());
    }

    @Test
    void filterFlightsLogs_gatewayReturnsNull_throwsNullPointerException() {
        // Arrange
        int page = 0;
        int rpp = 10;
        
        when(flightsLogsGateway.findAll(page, rpp)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, 
            () -> flightsLogsUseCase.filterFlightsLogs(page, rpp));
        
        verify(flightsLogsGateway).findAll(page, rpp);
        verify(loggerGateway).info(anyString());
    }

    @Test
    void filterById_existingId_returnsFlightLog() {
        // Arrange
        Integer id = 1;
        FlightLogModel flightLog = createFlightLogModel(id, "LIS,OPO", "EUR", "2025-06-14", "2025-06-21");
        
        when(flightsLogsGateway.findById(id)).thenReturn(Optional.of(flightLog));

        // Act
        Optional<FlightLogsOutputDto> result = flightsLogsUseCase.filterById(id);

        // Assert
        assertTrue(result.isPresent());
        FlightLogsOutputDto outputDto = result.get();
        assertEquals("LIS,OPO", outputDto.flyTo());
        assertEquals("EUR", outputDto.currency());
        assertEquals("2025-06-14", outputDto.dateFrom());
        assertEquals("2025-06-21", outputDto.dateTo());
        assertNotNull(outputDto.recordDateTime());
        
        verify(flightsLogsGateway).findById(id);
    }

    @Test
    void filterById_nonExistingId_throwsFlightsDataNotFoundException() {
        // Arrange
        Integer id = 999;
        
        when(flightsLogsGateway.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FlightsDataNotFoundException.class, 
            () -> flightsLogsUseCase.filterById(id));
        
        verify(flightsLogsGateway).findById(id);
    }

    @Test
    void filterById_gatewayReturnsNull_throwsNullPointerException() {
        // Arrange
        Integer id = 1;
        
        when(flightsLogsGateway.findById(id)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, 
            () -> flightsLogsUseCase.filterById(id));
        
        verify(flightsLogsGateway).findById(id);
    }

    @Test
    void deleteAll_successfulDeletion_completesWithoutException() {
        // Arrange
        doNothing().when(flightsLogsGateway).deleteAll();

        // Act & Assert
        assertDoesNotThrow(() -> flightsLogsUseCase.deleteAll());
        
        verify(flightsLogsGateway).deleteAll();
        verify(loggerGateway).info(anyString());
    }

    @Test
    void deleteAll_gatewayThrowsException_throwsDeleteFlightLogsException() {
        // Arrange
        doThrow(new RuntimeException("Database error")).when(flightsLogsGateway).deleteAll();

        // Act & Assert
        DeleteFlightLogsException exception = assertThrows(DeleteFlightLogsException.class, 
            () -> flightsLogsUseCase.deleteAll());
        
        assertNotNull(exception.getCause());
        assertEquals("Database error", exception.getCause().getMessage());
        
        verify(flightsLogsGateway).deleteAll();
        verify(loggerGateway).info(anyString());
    }

    @Test
    void deleteById_existingId_returnsTrue() {
        // Arrange
        Object id = 1;
        FlightLogModel flightLog = createFlightLogModel(1, "LIS,OPO", "EUR", "2025-06-14", "2025-06-21");
        
        when(flightsLogsGateway.findById(id)).thenReturn(Optional.of(flightLog));
        doNothing().when(flightsLogsGateway).deleteById(id);

        // Act
        boolean result = flightsLogsUseCase.deleteById(id);

        // Assert
        assertTrue(result);
        verify(flightsLogsGateway).findById(id);
        verify(flightsLogsGateway).deleteById(id);
        verify(loggerGateway).info(anyString());
    }

    @Test
    void deleteById_nonExistingId_returnsFalse() {
        // Arrange
        Object id = 999;
        
        when(flightsLogsGateway.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = flightsLogsUseCase.deleteById(id);

        // Assert
        assertFalse(result);
        verify(flightsLogsGateway).findById(id);
        verify(flightsLogsGateway, never()).deleteById(id);
        verify(loggerGateway).info(anyString());
    }

    @Test
    void deleteById_findByIdThrowsException_throwsDeleteFlightLogsException() {
        // Arrange
        Object id = 1;
        
        when(flightsLogsGateway.findById(id)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        DeleteFlightLogsException exception = assertThrows(DeleteFlightLogsException.class, 
            () -> flightsLogsUseCase.deleteById(id));
        
        assertNotNull(exception.getCause());
        assertEquals("Database error", exception.getCause().getMessage());
        
        verify(flightsLogsGateway).findById(id);
        verify(flightsLogsGateway, never()).deleteById(id);
        verify(loggerGateway).info(anyString());
    }

    @Test
    void deleteById_deleteByIdThrowsException_throwsDeleteFlightLogsException() {
        // Arrange
        Object id = 1;
        FlightLogModel flightLog = createFlightLogModel(1, "LIS,OPO", "EUR", "2025-06-14", "2025-06-21");
        
        when(flightsLogsGateway.findById(id)).thenReturn(Optional.of(flightLog));
        doThrow(new RuntimeException("Delete error")).when(flightsLogsGateway).deleteById(id);

        // Act & Assert
        DeleteFlightLogsException exception = assertThrows(DeleteFlightLogsException.class, 
            () -> flightsLogsUseCase.deleteById(id));
        
        assertNotNull(exception.getCause());
        assertEquals("Delete error", exception.getCause().getMessage());
        
        verify(flightsLogsGateway).findById(id);
        verify(flightsLogsGateway).deleteById(id);
        verify(loggerGateway).info(anyString());
    }

    @Test
    void deleteById_stringId_handlesCorrectly() {
        // Arrange
        Object id = "1";
        FlightLogModel flightLog = createFlightLogModel(1, "LIS,OPO", "EUR", "2025-06-14", "2025-06-21");
        
        when(flightsLogsGateway.findById(id)).thenReturn(Optional.of(flightLog));
        doNothing().when(flightsLogsGateway).deleteById(id);

        // Act
        boolean result = flightsLogsUseCase.deleteById(id);

        // Assert
        assertTrue(result);
        verify(flightsLogsGateway).findById(id);
        verify(flightsLogsGateway).deleteById(id);
        verify(loggerGateway).info(anyString());
    }

    @Test
    void deleteById_nullId_returnsFalse() {
        // Arrange
        Object id = null;
        
        when(flightsLogsGateway.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = flightsLogsUseCase.deleteById(id);

        // Assert
        assertFalse(result);
        verify(flightsLogsGateway).findById(id);
        verify(flightsLogsGateway, never()).deleteById(id);
        verify(loggerGateway).info(anyString());
    }

    private FlightLogModel createFlightLogModel(Integer id, String flyTo, String currency, String dateFrom, String dateTo) {
        return new FlightLogModel(id, flyTo, currency, dateTo, dateFrom, LocalDateTime.now());
    }
}