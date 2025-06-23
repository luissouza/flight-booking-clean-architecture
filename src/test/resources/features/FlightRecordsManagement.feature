Feature: Flight Records Management

  @FlightRecordsScenarioSteps
  Scenario: Get all flight records when database is empty
    Given the flight records database is empty
    When I request all flight records
    Then I should receive an empty list of flight records
    And the response status should be 200

  @FlightRecordsScenarioSteps
  Scenario: Create and retrieve flight records
    Given the flight records database is empty
    When I create a flight record with flyTo "LIS" and currency "EUR"
    And I request all flight records
    Then I should receive a list with 1 flight record
    And the response status should be 200

  @FlightRecordsScenarioSteps
  Scenario: Delete all flight records
    Given I have 3 flight records in the database
    When I delete all flight records
    Then the response status should be 204
    And the flight records database should be empty

  @FlightRecordsScenarioSteps
  Scenario: Delete flight record by ID
    Given I have a flight record with ID 1 in the database
    When I delete the flight record with ID 1
    Then the response status should be 204
    And the flight record with ID 1 should not exist

  @FlightRecordsScenarioSteps
  Scenario: Delete non-existent flight record
    Given the flight records database is empty
    When I delete the flight record with ID 999
    Then the response status should be 404

  @FlightRecordsScenarioSteps
  Scenario: Get flight records with pagination
    Given I have 5 flight records in the database
    When I request flight records with page 0 and rpp 2
    Then I should receive a list with flight records
    And the response status should be 200