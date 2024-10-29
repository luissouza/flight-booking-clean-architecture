package org.pt.flightbooking.adapters.datasources.repository;

import jakarta.transaction.Transactional;
import org.pt.flightbooking.adapters.schema.jpa.FlightLogsJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface JpaFlightsLogsRepository extends JpaRepository<FlightLogsJpa, Integer> {
}
