Feature: Fetch flights

  @FlightScenarioSteps
  Scenario: fetch flights in skyPicker
    Given a flight search is submitted
    When the flight is successfully submitted
    And at least one flight exist
    Then the feature is already operating


