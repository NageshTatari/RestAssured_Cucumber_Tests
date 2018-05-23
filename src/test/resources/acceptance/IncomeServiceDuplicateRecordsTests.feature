Feature: Income service should remove duplicate records for business analysis

  As a calling system
  I want income service to remove duplicate records to avoid confusion
  So that I can use this information in my business logic


  @IncomeServiceDuplicates
  Scenario: A duplicate

    Given There is a citizen with 2 RTI records with all fields identical and one distinct record
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And the response has 2 identical RTI records with unique payment ID 301
    And the response has 1 RTI record which is distinct with ID 302
    And the response has 1 income streams
    And one copy of the duplicate records is removed from prepared income
    And the response has 1 observations
    And observation for duplicate record removed relating to unique payment id 301


  @IncomeServiceDuplicates
  Scenario: Many duplicates

    Given There is a citizen with 8 RTI records and one set of two duplicates with employer one and one set of five duplicates with employer two and one distinct record with employer three
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 8 RTI records
    And RTI record 0 has unique payment id 101
    And RTI record 1 duplicate of record 0 with id 101
    And RTI record 2 has unique payment id 201
    And RTI record 3 duplicate of record 2 with id 201
    And RTI record 4 duplicate of record 2 with id 201
    And RTI record 5 duplicate of record 2 with id 201
    And RTI record 6 duplicate of record 2 with id 201
    And RTI record 7 has unique payment id 301
    And the response has 3 income streams
    And all employers duplicate records is removed from prepared income
    And the response has 1 prepared income records in income stream 0
    And the response has 1 prepared income records in income stream 1
    And the response has 1 prepared income records in income stream 2
    And the response has 5 observations
    And observation for duplicate record removed relating to unique payment id 101
    And observation for duplicate record removed relating to unique payment id 201


  @IncomeServiceDuplicates
  Scenario: No duplicates

    Given There is a citizen with 4 RTI records where not all fields are identical
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 4 RTI records
    And the response has 4 RTI records where not all fields are identical
    And the response has 1 income streams
    And the response has 4 prepared income records in income stream 0
    And the response has 1 observations
    And it is an all good data observation