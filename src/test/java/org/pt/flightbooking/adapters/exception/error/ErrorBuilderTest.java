package org.pt.flightbooking.adapters.exception.error;

import org.junit.jupiter.api.Test;
import org.pt.flightbooking.adapters.exception.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ErrorBuilderTest {

    private final ErrorBuilder errorBuilder = new ErrorBuilder();

    @Test
    void createError_ShouldCreateErrorResponseWithCorrectValues() {
        // Arrange
        String message = "Test error message";
        String exception = "TestException";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Act
        ErrorResponseDto errorResponse = errorBuilder.createError(message, exception, status);

        // Assert
        assertNotNull(errorResponse);
        assertEquals(status.value(), errorResponse.getHttpCode());
        assertEquals(status.getReasonPhrase(), errorResponse.getMessage());
        assertEquals(exception, errorResponse.getException());
        assertEquals(message, errorResponse.getDetail());
        
        // Verify timestamp format
        String timestamp = errorResponse.getTimestamp();
        assertNotNull(timestamp);
        
        // Verify the timestamp is in ISO_LOCAL_DATE_TIME format
        LocalDateTime parsedTimestamp = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertNotNull(parsedTimestamp);
        
        // Verify the timestamp is close to current time (within 5 seconds)
        LocalDateTime now = LocalDateTime.now();
        assertTrue(parsedTimestamp.isAfter(now.minusSeconds(5)));
        assertTrue(parsedTimestamp.isBefore(now.plusSeconds(5)));
    }

    @Test
    void createError_WithNullValues_ShouldHandleNullsGracefully() {
        // Act
        ErrorResponseDto errorResponse = errorBuilder.createError(null, null, HttpStatus.INTERNAL_SERVER_ERROR);

        // Assert
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getHttpCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errorResponse.getMessage());
        assertNull(errorResponse.getException());
        assertNull(errorResponse.getDetail());
        assertNotNull(errorResponse.getTimestamp());
    }

    @Test
    void createError_WithDifferentHttpStatuses_ShouldReflectCorrectStatus() {
        // Test with NOT_FOUND status
        ErrorResponseDto notFoundError = errorBuilder.createError("Resource not found", "NotFoundException", HttpStatus.NOT_FOUND);
        assertEquals(HttpStatus.NOT_FOUND.value(), notFoundError.getHttpCode());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), notFoundError.getMessage());

        // Test with FORBIDDEN status
        ErrorResponseDto forbiddenError = errorBuilder.createError("Access denied", "ForbiddenException", HttpStatus.FORBIDDEN);
        assertEquals(HttpStatus.FORBIDDEN.value(), forbiddenError.getHttpCode());
        assertEquals(HttpStatus.FORBIDDEN.getReasonPhrase(), forbiddenError.getMessage());
    }
}