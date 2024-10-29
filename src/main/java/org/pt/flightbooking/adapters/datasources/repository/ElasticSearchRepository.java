package org.pt.flightbooking.adapters.datasources.repository;

import org.pt.flightbooking.adapters.schema.elastic.FlightLogsEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchRepository extends ElasticsearchRepository<FlightLogsEs, String>  {


}