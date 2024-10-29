package org.pt.flightbooking.adapters.datasources.repository;

import org.pt.flightbooking.adapters.configuration.ApplicationConfigProperties;
import org.pt.flightbooking.adapters.dto.FlightLogs;
import org.pt.flightbooking.adapters.schema.elastic.FlightLogsEs;
import org.pt.flightbooking.adapters.schema.jpa.FlightLogsJpa;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FlightsLogsRepositoryImpl implements FlightLogsRepository {

	final ApplicationConfigProperties applicationConfigProperties;
	final JpaFlightsLogsRepository jpaFlightsLogsRepository;
	final ElasticSearchRepository elasticSearchRepository;

	public FlightsLogsRepositoryImpl(ApplicationConfigProperties applicationConfigProperties, final JpaFlightsLogsRepository jpaFlightsLogsRepository, ElasticSearchRepository elasticSearchRepository) {
		this.applicationConfigProperties = applicationConfigProperties;
		this.jpaFlightsLogsRepository = jpaFlightsLogsRepository;
		this.elasticSearchRepository = elasticSearchRepository;
	}

	@Override
	public List<FlightLogs> findAll() {

		List<FlightLogs> results = new ArrayList<>();

		switch (applicationConfigProperties.getDatabaseDefault().toLowerCase()) {
			case "postgresql" -> jpaFlightsLogsRepository.findAll()
					.forEach(entity -> results.add(new FlightLogs(entity.getFlyTo(), entity.getCurrency(), entity.getDateTo(), entity.getDateFrom(), entity.getRecordDateTime())));
			case "elasticsearch" -> elasticSearchRepository.findAll()
					.forEach(entity -> results.add(new FlightLogs(entity.getFlyTo(), entity.getCurrency(), entity.getDateTo(), entity.getDateFrom(), entity.getRecordDateTime())));
			default ->
					throw new IllegalArgumentException(applicationConfigProperties.getDatabaseDefault().toLowerCase());
		}

		return results;
	}

	@Override
	public List<FlightLogs> findAll(int page, int rpp) {
		List<FlightLogs> results = new ArrayList<>();

		switch (applicationConfigProperties.getDatabaseDefault().toLowerCase()) {
			case "postgresql" -> jpaFlightsLogsRepository.findAll()
					.forEach(entity -> results.add(new FlightLogs(entity.getFlyTo(),
														          entity.getCurrency(),
															      entity.getDateTo(),
																  entity.getDateFrom(),
																  entity.getRecordDateTime())));
			case "elasticsearch" -> elasticSearchRepository.findAll()
					.forEach(entity -> results.add(new FlightLogs(entity.getFlyTo(),
																  entity.getCurrency(),
																  entity.getDateTo(),
																  entity.getDateFrom(),
																  entity.getRecordDateTime())));
			default ->
					throw new IllegalArgumentException(applicationConfigProperties.getDatabaseDefault().toLowerCase());
		}
		return results;
	}

	@Override
	public Optional<FlightLogs> findById(Object id) {
		FlightLogs result;
		switch (applicationConfigProperties.getDatabaseDefault().toLowerCase()) {
			case "postgresql" -> {
				Optional<FlightLogsJpa> resultJpa = jpaFlightsLogsRepository.findById((Integer) id);
				result = new FlightLogs(resultJpa.get().getFlyTo(), resultJpa.get().getCurrency(), resultJpa.get().getDateTo(), resultJpa.get().getDateFrom(), resultJpa.get().getRecordDateTime());
			}
			case "elasticsearch" -> {
				Optional<FlightLogsEs> resultEs = elasticSearchRepository.findById(String.valueOf(id));
				result = new FlightLogs(resultEs.get().getFlyTo(), resultEs.get().getCurrency(), resultEs.get().getDateTo(), resultEs.get().getDateFrom(), resultEs.get().getRecordDateTime());
			}
			default ->
					throw new IllegalArgumentException(applicationConfigProperties.getDatabaseDefault().toLowerCase());
		}

		return Optional.of(result);
	}

	@Override
	public void deleteAll() {
		switch (applicationConfigProperties.getDatabaseDefault().toLowerCase()) {
			case "postgresql" -> jpaFlightsLogsRepository.deleteAll();
			case "elasticsearch" -> elasticSearchRepository.deleteAll();
			default ->
					throw new IllegalArgumentException(applicationConfigProperties.getDatabaseDefault().toLowerCase());
		}
	}

	@Override
	public void deleteById(Object id) {
		switch (applicationConfigProperties.getDatabaseDefault().toLowerCase()) {
			case "postgresql" -> jpaFlightsLogsRepository.deleteById((Integer) id);
			case "elasticsearch" -> elasticSearchRepository.deleteById(String.valueOf(id));
			default ->
					throw new IllegalArgumentException(applicationConfigProperties.getDatabaseDefault().toLowerCase());
		}
	}

	@Override
	public void create(FlightLogs record) {
		switch (applicationConfigProperties.getDatabaseDefault().toLowerCase()) {
			case "postgresql" -> {
				FlightLogsJpa dataJpa = new FlightLogsJpa(record.getFlyTo(), record.getCurrency(), record.getDateTo(), record.getDateFrom(), record.getRecordDateTime());
				jpaFlightsLogsRepository.save(dataJpa);
			}
			case "elasticsearch" -> {
				FlightLogsEs dataEs = new FlightLogsEs(UUID.randomUUID().toString(), record.getFlyTo(), record.getCurrency(), record.getDateTo(), record.getDateFrom(), record.getRecordDateTime());
				elasticSearchRepository.save(dataEs);
			}
			default ->
					throw new IllegalArgumentException(applicationConfigProperties.getDatabaseDefault().toLowerCase());
		}
	}
}
