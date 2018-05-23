Feature: Income service should identify when a Year To Date Reset has occurred

  As a calling system
  I want to know when a Year to Date Reset has occurred
  so that I can take this into account in my calculations

  @YearToDateReset
  Scenario: Year To Date reset has occurred within requested period, all relevant values equal

    Given there is a citizen with three RTI records for a time period with all relevant values equal
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And the response has 1 income streams
    And income stream 0 has 3 income items
    And the response has 3 observations
    And non sequential record observation for unique payment id 103 exists
    And non sequential record set observation for record set DWP111 111 111 1 exists
    And year to date reset observation for unique payment id 103 exists


  @YearToDateReset
  Scenario: Multiple Year To Date resets have occurred outside requested period, all relevant values equal

    Given there is a citizen with two RTI records for a time period with multiple year to date resets outside requested period
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And the response has 1 income streams
    And income stream 0 has 2 income items
    And the response has 1 observations
    And year to date reset has occurred before requested time period observation for record set DWP111 111 111 1 exists


  @YearToDateReset
  Scenario: Multiple Year To Date resets have occurred within requested period, all relevant values equal

    Given there is a citizen with three RTI records for a time period with multiple year to date resets inside requested period
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And the response has 1 income streams
    And income stream 0 has 3 income items
    And the response has 5 observations
    And non sequential record observation for unique payment id 103 exists
    And non sequential record observation for unique payment id 104 exists
    And non sequential record set observation for record set DWP111 111 111 1 exists
    And year to date reset observation for unique payment id 103 exists
    And year to date reset observation for unique payment id 104 exists


  @YearToDateReset
  Scenario: First record of employment, all relevant fields equal

    Given there is a citizen with one RTI records for a time period which is first in the employment
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And the response has 1 income streams
    And income stream 0 has 1 income items
    And the response has 1 observations
    And it is an all good data observation


  @YearToDateReset
  Scenario: First record within requested period, YTD reset has occurred

    Given there is a citizen with three RTI records for a time period, first record within requested period and year to date reset has occurred
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And the response has 1 income streams
    And income stream 0 has 3 income items
    And the response has 4 observations
    And non sequential record observation for unique payment id 104 exists
    And non sequential record set observation for record set DWP111 111 111 1 exists
    And year to date reset observation for unique payment id 104 exists
    And year to date reset has occurred before requested time period observation for record set DWP111 111 111 1 exists


  @YearToDateReset
  Scenario: All relevant values equal except tax deducted!= Total tax to date

    Given there is a citizen with three RTI records for a time period and all relevant values equal except tax deducted!= Total tax to date
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And the response has 1 income streams
    And income stream 0 has 1 income items
    And the response has 1 observations
    And it is an all good data observation


  @YearToDateReset
  Scenario: All relevant values equal except employees contributions != employees NI contribution to date

    Given there is a citizen with three RTI records for a time period and all relevant values equal except employees contributions != employees NI contribution to date
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And the response has 1 income streams
    And income stream 0 has 1 income items
    And the response has 1 observations
    And it is an all good data observation


  @YearToDateReset
  Scenario: All relevant values equal except latest taxable pay to date <= previous taxable pay to date

    Given there is a citizen with three RTI records for a time period and all relevant values equal except latest taxable pay to date <= previous taxable pay to date
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And the response has 1 income streams
    And income stream 0 has 1 income items
    And the response has 1 observations
    And it is an all good data observation


  @YearToDateReset
  Scenario: Multiple income streams across 2 tax years, Multiple Year To Date resets have occurred, all relevant values equal

    Given there is a citizen with eight RTI records for a time period across two tax years, multiple income streams, reset inside requested period
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 8 RTI records
    And the response has 2 income streams
    And income stream 0 has 4 income items
    And income stream 1 has 4 income items
    And the response has 10 observations
    And non sequential record observation for unique payment id 201 exists
    And non sequential record observation for unique payment id 203 exists
    And non sequential record observation for unique payment id 206 exists
    And non sequential record observation for unique payment id 208 exists
    And non sequential record set observation for record set DWP111 111 111 1 exists
    And non sequential record set observation for record set DWP111 111 111 2 exists
    And year to date reset observation for unique payment id 201 exists
    And year to date reset observation for unique payment id 203 exists
    And year to date reset observation for unique payment id 206 exists
    And year to date reset observation for unique payment id 208 exists


  @YearToDateReset
  Scenario: Multiple Year To Date resets have occurred with in and outside requested dates, Multiple income streams across 2 tax years,  all relevant values equal,

    Given there is a citizen with six RTI records for a time period across two tax years, multiple income streams, reset outside requested period
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 6 RTI records
    And the response has 2 income streams
    And income stream 0 has 3 income items
    And income stream 1 has 3 income items
    And the response has 8 observations
    And non sequential record observation for unique payment id 213 exists
    And non sequential record observation for unique payment id 218 exists
    And non sequential record set observation for record set DWP111 111 111 1 exists
    And non sequential record set observation for record set DWP111 111 111 2 exists
    And year to date reset observation for unique payment id 218 exists
    And year to date reset observation for unique payment id 213 exists
    And year to date reset has occurred before requested time period observation for record set DWP111 111 111 1 exists
    And year to date reset has occurred before requested time period observation for record set DWP111 111 111 2 exists