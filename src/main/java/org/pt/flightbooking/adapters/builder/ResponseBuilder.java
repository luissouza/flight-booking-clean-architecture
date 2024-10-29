package org.pt.flightbooking.adapters.builder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseBuilder {
    ResponseEntity<Object> build(Object data, long timeStamp, HttpStatus status);
}