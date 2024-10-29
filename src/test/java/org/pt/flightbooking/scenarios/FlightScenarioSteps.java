package org.pt.flightbooking.scenarios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.pt.flightbooking.application.dto.FlightHeaderResponseDto;
import org.pt.flightbooking.application.dto.request.FlightSearchInputDto;
import org.pt.flightbooking.configuration.ConfigurationAutomatedTest;
import org.pt.flightbooking.configuration.ScenarioContext;
import org.pt.flightbooking.enums.Context;

public class FlightScenarioSteps extends ConfigurationAutomatedTest {

  ScenarioContext scenarioContext;
  Scenario scenario;

  public FlightScenarioSteps(final ScenarioContext context) {
    this.scenarioContext = context;
  }

  @Before(value = "@FlightScenarioSteps")
  public void beforeScenario(final Scenario scenario) {
    this.scenario = scenario;
    scenarioContext.clearContext();
  }

  @After(value = "@FlightScenarioSteps")
  public void afterScenario(final Scenario scenario) {
    this.scenario = scenario;
  }

  @Given("a flight search is submitted")
  public void given_a_flight_search_is_submitted() {
    try {

      final String flyTo = "LIS,OPO";
      final String flyFrom = "OPO,LIS";
      final String currency = "GBP";
      final String dateFrom = "2023-03-01";
      final String dateTo = "2023-03-03";
      final String airLines = "TP,FR";

      final FlightSearchInputDto params = new FlightSearchInputDto(currency, dateFrom, dateTo, flyTo, flyFrom, airLines);

      final var response = (FlightHeaderResponseDto) this.flightsService.filterFlights(params);

      if (response != null) {
        scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, Boolean.TRUE);
        scenarioContext.setContext(Context.QUANTITY_FLIGHTS_IN_SEARCH, response.averageFlights().entrySet().size());
        assertThat("Average flights success", Boolean.TRUE, is(Boolean.TRUE));
      } else {
        scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, Boolean.FALSE);
      }

    } catch (final Exception e) {
      assertThat("Error to fetch average flights", Boolean.FALSE, is(Boolean.TRUE));
    }
  }

  @When("the flight is successfully submitted")
  public void when_the_flight_successfully_is_submitted() {
    try {
      assertThat("The status of hiker submission is not the expected ",
          (Boolean) scenarioContext.getContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS), is(Boolean.TRUE));
    } catch (final Exception e) {
      assertThat("Error to submit hiker", Boolean.FALSE, is(Boolean.TRUE));
    }
  }

  @And("at least one flight exist")
  public void and_at_least_one_flight_exist() {
    try {
      assertThat("The status of hiker submission is not the expected ",
          (Integer) scenarioContext.getContext(Context.QUANTITY_FLIGHTS_IN_SEARCH) > 0 ? Boolean.TRUE : Boolean.FALSE,
          is(Boolean.TRUE));
    } catch (final Exception e) {
      assertThat("Error to create hiker", Boolean.FALSE, is(Boolean.TRUE));
    }
  }

  @Then("the feature is already operating")
  public void then_feature_is_already_operating() {
    assertThat("Success", Boolean.TRUE, is(Boolean.TRUE));
  }

}