Feature: Income service should return citizens income information

  As a calling system
  I want income data for a citizen
  So that I can use this information in my business logic


  @IncomeService
  Scenario Outline: Single income stream

    Given There is a citizen with NINO <nino> and RTI data for the period between <start> and <end> dates
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has <number> RTI records
    And the response has 1 income streams
    And the response has <number> prepared income records in income stream 0 between <start> and <end> dates
    And the response has 1 observations
    And it is an all good data observation

    Examples:
      | nino      | start      | end        | number |
      | QQ111111A | 2015-04-01 | 2015-05-01 | 1      |
      | QQ111111A | 2015-04-01 | 2015-06-01 | 2      |
      | QQ111111A | 2015-04-01 | 2015-08-01 | 4      |


  @IncomeService
  Scenario Outline: Multiple income streams

    Given There is a citizen with NINO <nino> and RTI data for the period between <start> and <end> dates
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 4 RTI records
    And the response has 3 income streams
    And the response has 1 prepared income records in income stream 0 between 2015-08-30 and 2015-09-01 dates
    And the response has 2 prepared income records in income stream 1 between 2015-09-30 and 2015-10-30 dates
    And the response has 1 prepared income records in income stream 2 between 2015-11-01 and 2015-11-30 dates
    And the response has 1 observations
    And it is an all good data observation

    Examples:
      | nino      | start      | end        |
      | QQ111112A | 2015-08-30 | 2015-11-30 |


  @IncomeService
  Scenario Outline: Income service should return citizen income data in json format

    Given There is a citizen with NINO <nino> and RTI data for the period between <start> and <end> dates
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And the response has 1 income streams
    And the response data is provided in json format
    And the response has 1 observations
    And it is an all good data observation

    Examples:
      | nino      | start      | end        |
      | QQ111111A | 2015-04-01 | 2015-05-01 |


  @IncomeService
  Scenario Outline: Income service should return agreed citizen income data fields

    Given There is a citizen with NINO <nino> and RTI data for the period between <start> and <end> dates
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And the response has 1 income streams
    And the response have agreed data fields are provided
    And the fields in the income streams match the equivalent fields in RTI
    And the response has 1 observations
    And it is an all good data observation

    Examples:
      | nino      | start      | end        |
      | QQ111111A | 2015-04-01 | 2015-05-01 |