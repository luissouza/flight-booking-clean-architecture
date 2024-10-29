package org.pt.flightbooking.application.usecases;

import org.pt.flightbooking.adapters.infrastructure.cache.CacheCustomKeys;
import org.pt.flightbooking.application.dto.FlightHeaderResponseDto;
import org.pt.flightbooking.application.dto.request.FlightSearchInputDto;
import org.springframework.cache.annotation.Cacheable;

public interface FlightUseCase {
    @Cacheable(value = "filterFlights", key=CacheCustomKeys.FILTER_FLIGHTS_KEY)
    FlightHeaderResponseDto filterFlights(FlightSearchInputDto params);
}

