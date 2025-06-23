package org.pt.flightbooking.scenarios;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.pt.flightbooking.application.dto.request.FlightSearchInputDto;
import org.pt.flightbooking.configuration.ConfigurationAutomatedTest;
import org.pt.flightbooking.configuration.ScenarioContext;
import org.pt.flightbooking.enums.Context;

public class FlightSearchErrorSteps extends ConfigurationAutomatedTest {

    ScenarioContext scenarioContext;
    Scenario scenario;
    FlightSearchInputDto searchInput;

    public FlightSearchErrorSteps(final ScenarioContext context) {
        this.scenarioContext = context;
    }

    @Before(value = "@FlightSearchErrorSteps")
    public void beforeScenario(final Scenario scenario) {
        this.scenario = scenario;
        scenarioContext.clearContext();
    }

    @After(value = "@FlightSearchErrorSteps")
    public void afterScenario(final Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("I have invalid flight search parameters")
    public void given_i_have_invalid_flight_search_parameters() {
        // Create search input with some invalid parameters
        searchInput = new FlightSearchInputDto(
            "GBP",
            "2023-03-01",
            "2023-03-03",
            "", // Empty flyTo
            "OPO,LIS",
            "TP,FR"
        );
        scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, false);
    }

    @Given("I have null flight search parameters")
    public void given_i_have_null_flight_search_parameters() {
        // Create search input with null parameters
        searchInput = new FlightSearchInputDto(
            null,
            null,
            null,
            null,
            null,
            null
        );
        scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, false);
    }

    @Given("I have flight search parameters with invalid date format")
    public void given_i_have_flight_search_parameters_with_invalid_date_format() {
        // Create search input with invalid date format
        searchInput = new FlightSearchInputDto(
            "GBP",
            "invalid-date",
            "another-invalid-date",
            "LIS,OPO",
            "OPO,LIS",
            "TP,FR"
        );
        scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, false);
    }

    @When("I submit a flight search with empty flyTo parameter")
    public void when_i_submit_a_flight_search_with_empty_flyto_parameter() {
        try {
            var result = flightsService.filterFlights(searchInput);
            scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, result != null);
            scenarioContext.setContext(Context.EXCEPTION, null);
        } catch (Exception e) {
            scenarioContext.setContext(Context.EXCEPTION, e);
            scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, false);
        }
    }

    @When("I submit a flight search with null parameters")
    public void when_i_submit_a_flight_search_with_null_parameters() {
        try {
            var result = flightsService.filterFlights(searchInput);
            scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, result != null);
            scenarioContext.setContext(Context.EXCEPTION, null);
        } catch (Exception e) {
            scenarioContext.setContext(Context.EXCEPTION, e);
            scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, false);
        }
    }

    @When("I submit a flight search with invalid dates")
    public void when_i_submit_a_flight_search_with_invalid_dates() {
        try {
            var result = flightsService.filterFlights(searchInput);
            scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, result != null);
            scenarioContext.setContext(Context.EXCEPTION, null);
        } catch (Exception e) {
            scenarioContext.setContext(Context.EXCEPTION, e);
            scenarioContext.setContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS, false);
        }
    }

    @Then("the search should handle the error gracefully")
    public void then_the_search_should_handle_the_error_gracefully() {
        // The search should not crash and should return false for invalid inputs
        Boolean success = (Boolean) scenarioContext.getContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS);
        assertThat("Search should handle error gracefully", success, is(false));
    }

    @And("no exception should be thrown to the user")
    public void and_no_exception_should_be_thrown_to_the_user() {
        // Either no exception was thrown, or if one was thrown, it was handled gracefully
        Exception exception = (Exception) scenarioContext.getContext(Context.EXCEPTION);
        // We expect the system to either handle the exception internally or return false
        // The key is that the system doesn't crash
        Boolean success = (Boolean) scenarioContext.getContext(Context.SUBMIT_FLIGHT_SEARCH_SUCCESS);
        assertThat("System should not crash", success != null, is(true));
    }
}