Feature: Flight Search Error Handling

  @FlightSearchErrorSteps
  Scenario: Handle invalid flight search parameters
    Given I have invalid flight search parameters
    When I submit a flight search with empty flyTo parameter
    Then the search should handle the error gracefully
    And no exception should be thrown to the user

  @FlightSearchErrorSteps
  Scenario: Handle flight search with null parameters
    Given I have null flight search parameters
    When I submit a flight search with null parameters
    Then the search should handle the error gracefully
    And no exception should be thrown to the user

  @FlightSearchErrorSteps
  Scenario: Handle flight search with invalid date format
    Given I have flight search parameters with invalid date format
    When I submit a flight search with invalid dates
    Then the search should handle the error gracefully
    And no exception should be thrown to the user