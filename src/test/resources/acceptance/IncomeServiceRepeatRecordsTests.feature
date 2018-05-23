Feature: Income service should remove repeat records for business analysis

  As a calling system
  I want income service to remove repeat records to avoid confusion
  So that I can use this information in my business logic


  @RepeatRecords
  Scenario: Two records are returned and one is repeat

    Given there is a citizen with 2 RTI records for a time period with all fields identical apart from unique payment id
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And the response has 1 income streams
    And the repeat record is removed from income stream 0
    And income stream 0 has 1 income items
    And the response has 1 observations
    And repeat record observation for unique payment id 100 exists


  @RepeatRecords
  Scenario: Three records are returned and two are repeat

    Given there is a citizen with 3 RTI records for a time period with different unique pay id and all fields identical
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And the response has 1 income streams
    And the repeat records are removed from income stream 0
    And income stream 0 has 1 income items
    And the response has 2 observations
    And repeat record observation for unique payment id 200 exists
    And repeat record observation for unique payment id 202 exists


  @RepeatRecords
  Scenario: Four records are returned for two income streams, one in each stream is repeat

    Given there is a citizen with four RTI records for a time period with different unique pay id and two income streams each with 2 records with all fields identical
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 4 RTI records
    And the response has 2 income streams
    And the repeat record is removed from income stream 0
    And the repeat record is removed from income stream 1
    And income stream 0 has 1 income items
    And income stream 1 has 1 income items
    And the response has 2 observations
    And repeat record observation for unique payment id 300 exists
    And repeat record observation for unique payment id 400 exists