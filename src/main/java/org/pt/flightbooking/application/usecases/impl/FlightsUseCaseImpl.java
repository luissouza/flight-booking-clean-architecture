package org.pt.flightbooking.application.usecases.impl;

import static java.util.stream.Collectors.groupingBy;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.pt.flightbooking.application.exception.AverageFlightsException;
import org.pt.flightbooking.application.exception.FlightDestinyException;
import org.pt.flightbooking.application.mappers.FlightInputMapper;
import org.pt.flightbooking.application.mappers.FlightMapper;
import org.pt.flightbooking.application.mappers.LocationMapper;
import org.pt.flightbooking.application.usecases.FlightUseCase;
import org.pt.flightbooking.application.dto.request.FlightSearchInputDto;
import org.pt.flightbooking.application.dto.FlightBagPriceAverageDto;
import org.pt.flightbooking.application.dto.FlightDetailsDto;
import org.pt.flightbooking.application.dto.FlightHeaderResponseDto;
import org.pt.flightbooking.application.dto.response.FlightResponseDto;
import org.pt.flightbooking.application.dto.FlightResumeDetailsDto;
import org.pt.flightbooking.application.dto.LocationDto;
import org.pt.flightbooking.application.utils.DateTimeFormatterConfig;
import org.pt.flightbooking.application.utils.NumberUtilsConfig;
import org.pt.flightbooking.application.utils.StringUtils;
import org.pt.flightbooking.entities.model.FlightDetails;
import org.pt.flightbooking.entities.model.FlightLogModel;
import org.pt.flightbooking.entities.gateway.FlightsLogsGateway;
import org.pt.flightbooking.entities.dto.FlightSearchInput;
import org.pt.flightbooking.entities.gateway.FlightsGateway;
import org.pt.flightbooking.entities.gateway.LoggerGateway;
import org.pt.flightbooking.entities.model.FlightModel;

public class FlightsUseCaseImpl implements FlightUseCase {

  private final FlightsGateway flightsGateway;
  private final FlightsLogsGateway flightsLogsGateway;
  private final LoggerGateway loggerGateway;

  public FlightsUseCaseImpl(final FlightsGateway flightsGateway, final FlightsLogsGateway flightsLogsGateway, final LoggerGateway loggerGateway) {
    this.flightsGateway = flightsGateway;
    this.flightsLogsGateway = flightsLogsGateway;
    this.loggerGateway = loggerGateway;
  }

    @Override
    public FlightHeaderResponseDto filterFlights(final FlightSearchInputDto params) {

      loggerGateway.info("filterFlights - Method Filter Flights Started: {} ", params.toString());

      try {

        validateAirports(params);
        FlightSearchInputDto inputParams = new FlightSearchInputDto(params.currency(), params.dateFrom(), params.dateTo(), params.flyTo(), params.flyTo(), params.airLines());
        final FlightResponseDto flightsDto = getFlights(inputParams);
        final Map<String, List<FlightDetailsDto>> flightsAggPerDestination = groupFlightsByDestiny(flightsDto);
        final Map<String, FlightResumeDetailsDto> resume = new HashMap<>();
        flightsAggPerDestination.forEach((key, value) -> calcAvg(resume, key, value, flightsDto));
        this.saveRecord(inputParams);

        final FlightHeaderResponseDto flightHeaderResponseDto = new FlightHeaderResponseDto(resume, DateTimeFormatterConfig.convertIsoFormat(params.dateFrom()), DateTimeFormatterConfig.convertIsoFormat(params.dateTo()));
        loggerGateway.info("Flights AVG response {} ", flightHeaderResponseDto.toString());

        return flightHeaderResponseDto;

      } catch (final Exception e) {
        throw new AverageFlightsException(e);
      }
  }

  public FlightResponseDto getFlights(final FlightSearchInputDto inputDto) {
    FlightSearchInput input = FlightInputMapper.toDto(inputDto);
    FlightModel flight = flightsGateway.getFlights(input);
    final FlightResponseDto response = FlightMapper.toDto(flight);
    loggerGateway.info("Flights from skyPicker {} ", Objects.requireNonNull(response).toString());
    return response;
  }

  public Map<String, List<FlightDetailsDto>> groupFlightsByDestiny(final FlightResponseDto dto) {
    return dto.data().stream().collect(groupingBy(FlightDetailsDto::flyTo));
  }

  public void saveRecord(final FlightSearchInputDto params) {
    final FlightLogModel record = new FlightLogModel(params.flyTo(), params.currency(), params.dateTo(), params.dateFrom(), LocalDateTime.now());
    flightsLogsGateway.create(record);
  }

  public void calcAvg(final Map<String, FlightResumeDetailsDto> res, final String destination,
      final List<FlightDetailsDto> flights, final FlightResponseDto flightsDto) throws AverageFlightsException {

    try {

      final FlightDetailsDto flyDetails = flights.stream().findFirst().get();

      final Double priceAvg = flights.stream().collect(Collectors.averagingDouble(FlightDetailsDto::price));

      final Double bagOneAvg = flights.stream().collect(Collectors.averagingDouble(p -> (p.baggage().bagOnePrice() == null ? 0.00 : p.baggage().bagOnePrice())));

      final Double bagTwoAvg = flights.stream().collect(Collectors.averagingDouble(p -> (p.baggage().bagTwoPrice() == null ? 0.00 : p.baggage().bagTwoPrice())));

      final FlightBagPriceAverageDto bagsAverage = new FlightBagPriceAverageDto(NumberUtilsConfig.round(bagOneAvg), NumberUtilsConfig.round(bagTwoAvg));

      final FlightResumeDetailsDto detailsFlyTo = new FlightResumeDetailsDto(flyDetails.cityTo(), flightsDto.currency(), NumberUtilsConfig.round(priceAvg), bagsAverage);
      res.put(destination, detailsFlyTo);

    } catch (final Exception e) {
      throw new AverageFlightsException(e);
    }

  }

  public String validateAirports(final FlightSearchInputDto params) {

    try {

      final String[] airPorts = params.flyTo().trim().split(",");

      if (airPorts.length != 2) {
        throw new FlightDestinyException(
            "The flight codes is invalid. Please insert TWO AIRPORT CODES separated by commas, "
                + "example: (OPO,LIS) or (LIS,OPO) to fetch data from PORTO and LISBON flights. "
                + "Consult link: https://airportcodes.aero/iata/ and see if the codes are valid.");
      }

      final String locationCodeOne = StringUtils.replaceSpecialChars(airPorts[0]);
      final String locationCodeTwo = StringUtils.replaceSpecialChars(airPorts[1]);
      final String locations = locationCodeOne.concat(",").concat(locationCodeTwo);
      final LocationDto locationOne = LocationMapper.toDto(flightsGateway.getLocation(locationCodeOne));
      final LocationDto locationTwo = LocationMapper.toDto(flightsGateway.getLocation(locationCodeTwo));

      if (Objects.requireNonNull(locationOne).locations().size() == 0
          || Objects.requireNonNull(locationTwo).locations().size() == 0) {
        throw new FlightDestinyException("At least one of the airport codes are invalid. "
            + "Consult link: https://airportcodes.aero/iata/ and see if the codes are valid.");
      }

      return locations;

    } catch (final Exception e) {
      throw new FlightDestinyException(
          "The flight codes is invalid. Please insert two airport codes separated by commas,"
              + " example: (OPO,LIS) or (LIS,OPO) to fetch data from PORTO and LISBON flights. "
              + "Consult link: https://airportcodes.aero/iata/ and see if the codes are valid.", e);
    }
  }

}
