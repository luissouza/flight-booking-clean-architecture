package org.pt.flightbooking.adapters.builder;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pt.flightbooking.adapters.dto.Meta;
import org.pt.flightbooking.adapters.dto.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResponseBuilderImplTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ResponseBuilderImpl responseBuilder;

    private long timestamp;

    @BeforeEach
    void setUp() {
        timestamp = System.currentTimeMillis();
    }

    @Test
    void metaDefaultBuilder_ShouldReturnDefaultMeta() {
        // Act
        Meta meta = responseBuilder.metaDefaultBuilder();

        // Assert
        assertEquals(1, meta.getTotalPages());
        assertEquals(1, meta.getPage());
        assertEquals(10, meta.getRecordsPerPage());
        assertEquals(0, meta.getTotalRecords());
    }

    @Test
    void build_WithSimpleObject_ShouldReturnResponseWithObject() {
        // Arrange
        String testData = "Test Data";
        when(request.getParameter("page")).thenReturn(null);
        when(request.getParameter("rpp")).thenReturn(null);

        // Act
        ResponseEntity<Object> responseEntity = responseBuilder.build(testData, timestamp, HttpStatus.OK);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        Response<?> response = (Response<?>) responseEntity.getBody();
        assertNotNull(response);
        assertEquals(testData, response.getData());
        
        Meta meta = response.getMeta();
        assertNotNull(meta);
        assertEquals(1, meta.getTotalPages());
        assertEquals(1, meta.getPage());
        assertEquals(10, meta.getRecordsPerPage());
        assertEquals(0, meta.getTotalRecords());
        assertEquals("0.0.1", meta.getVersion());
        assertNotNull(meta.getDuration());
    }

    @Test
    void build_WithPageParameter_ShouldSetPageInMeta() {
        // Arrange
        String testData = "Test Data";
        when(request.getParameter("page")).thenReturn("2");
        when(request.getParameter("rpp")).thenReturn(null);

        // Act
        ResponseEntity<Object> responseEntity = responseBuilder.build(testData, timestamp, HttpStatus.OK);

        // Assert
        Response<?> response = (Response<?>) responseEntity.getBody();
        assertNotNull(response);
        Meta meta = response.getMeta();
        assertEquals(2, meta.getPage());
    }

    @Test
    void build_WithRecordsPerPageParameter_ShouldSetRecordsPerPageInMeta() {
        // Arrange
        String testData = "Test Data";
        when(request.getParameter("page")).thenReturn(null);
        when(request.getParameter("rpp")).thenReturn("20");

        // Act
        ResponseEntity<Object> responseEntity = responseBuilder.build(testData, timestamp, HttpStatus.OK);

        // Assert
        Response<?> response = (Response<?>) responseEntity.getBody();
        assertNotNull(response);
        Meta meta = response.getMeta();
        assertEquals(20, meta.getRecordsPerPage());
    }

    @Test
    void build_WithPageData_ShouldHandlePageCorrectly() {
        // Arrange
        List<String> items = Arrays.asList("Item1", "Item2", "Item3");
        Page<String> pageData = new PageImpl<>(items, PageRequest.of(0, 10), 3);
        when(request.getParameter("page")).thenReturn(null);
        when(request.getParameter("rpp")).thenReturn(null);

        // Act
        ResponseEntity<Object> responseEntity = responseBuilder.build(pageData, timestamp, HttpStatus.OK);

        // Assert
        Response<?> response = (Response<?>) responseEntity.getBody();
        assertNotNull(response);
        assertEquals(items, response.getData());
        
        Meta meta = response.getMeta();
        assertEquals(1, meta.getTotalPages());
        assertEquals(3, meta.getTotalRecords());
    }

    @Test
    void build_WithListData_ShouldHandleListCorrectly() {
        // Arrange
        List<String> items = Arrays.asList("Item1", "Item2", "Item3", "Item4", "Item5");
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("rpp")).thenReturn("2");

        // Act
        ResponseEntity<Object> responseEntity = responseBuilder.build(items, timestamp, HttpStatus.OK);

        // Assert
        Response<?> response = (Response<?>) responseEntity.getBody();
        assertNotNull(response);
        
        List<?> resultList = (List<?>) response.getData();
        assertEquals(2, resultList.size());
        assertEquals("Item1", resultList.get(0));
        assertEquals("Item2", resultList.get(1));
        
        Meta meta = response.getMeta();
        assertEquals(3, meta.getTotalPages()); // 5 items with 2 per page = 3 pages
        assertEquals(5, meta.getTotalRecords());
        assertEquals(1, meta.getPage());
        assertEquals(2, meta.getRecordsPerPage());
    }

    @Test
    void build_WithListDataAndSecondPage_ShouldReturnCorrectItems() {
        // Arrange
        List<String> items = Arrays.asList("Item1", "Item2", "Item3", "Item4", "Item5");
        when(request.getParameter("page")).thenReturn("2");
        when(request.getParameter("rpp")).thenReturn("2");

        // Act
        ResponseEntity<Object> responseEntity = responseBuilder.build(items, timestamp, HttpStatus.OK);

        // Assert
        Response<?> response = (Response<?>) responseEntity.getBody();
        assertNotNull(response);
        
        List<?> resultList = (List<?>) response.getData();
        assertEquals(2, resultList.size());
        assertEquals("Item3", resultList.get(0));
        assertEquals("Item4", resultList.get(1));
    }

    @Test
    void build_WithListDataAndLastPage_ShouldHandlePartialPage() {
        // Arrange
        List<String> items = Arrays.asList("Item1", "Item2", "Item3", "Item4", "Item5");
        when(request.getParameter("page")).thenReturn("3");
        when(request.getParameter("rpp")).thenReturn("2");

        // Act
        ResponseEntity<Object> responseEntity = responseBuilder.build(items, timestamp, HttpStatus.OK);

        // Assert
        Response<?> response = (Response<?>) responseEntity.getBody();
        assertNotNull(response);
        
        List<?> resultList = (List<?>) response.getData();
        assertEquals(1, resultList.size());
        assertEquals("Item5", resultList.get(0));
    }
}