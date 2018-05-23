Feature: Income service should identify occupational pension records

  As a calling system
  I want to receive income data for a citizen where occupational pension records are identified
  So that I can utilise this information in my business logic

  @OccupationalPension
  Scenario: Death Benefit Indicator tells us it is a pension

    Given there is a citizen with one RTI record for a time period with death indicator benefit set to true
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And RTI record 0 has the death benefit indicator is set to true
    And RTI record 0 has occupational pension indicator is set to false
    And RTI record 0 has FlexiblyAccessingPensionRightsIndicator is set to false
    And the response has 1 income streams
    And income stream 0 income item 0 has occupational pension flag set to true
    And the response has 1 observations
    And an occupational pension record observation for unique payment id 901 exists


  @OccupationalPension
  Scenario: Flexibly accessing pension rights indicator tells us it’s a pension

    Given there is a citizen with one RTI record for a time period with flexibly accessing pension rights indicator set to true
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And RTI record 0 has the death benefit indicator is set to false
    And RTI record 0 has occupational pension indicator is set to false
    And RTI record 0 has FlexiblyAccessingPensionRightsIndicator is set to true
    And the response has 1 income streams
    And income stream 0 income item 0 has occupational pension flag set to true
    And the response has 3 observations
    And an occupational pension record observation for unique payment id 903 exists
    And non sequential record observation for unique payment id 903 exists
    And non sequential record set observation for record set DWP1 101 003 101 exists


  @OccupationalPension
  Scenario: Occupational pension indicator tells us it’s a pension

    Given there is a citizen with one RTI record for a time period with occupational pension indicator set to true and all relevant fields equal to zero or null
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And RTI record 0 has the death benefit indicator is set to false
    And RTI record 0 has occupational pension flag set to true and all relevant fields equal to zero and null
    And RTI record 0 has FlexiblyAccessingPensionRightsIndicator is set to false
    And the response has 1 income streams
    And income stream 0 income item 0 has occupational pension flag set to true
    And the response has 1 observations
    And it is an all good data observation


  @OccupationalPension
  Scenario: Relevant fields tell us it’s not a pension

    Given there is a citizen with one RTI record for a time period with occupational pension indicator set to true and all relevant fields equal to values
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And RTI record 0 has the death benefit indicator is set to false
    And RTI record 0 has occupational pension flag set to true and all relevant fields equal to 100 and 100
    And RTI record 0 has FlexiblyAccessingPensionRightsIndicator is set to false
    And the response has 1 income streams
    And income stream 0 income item 0 has occupational pension flag set to false
    And the response has 3 observations
    And not an occupational pension record observation for unique payment id 905 exists
    And non sequential record observation for unique payment id 905 exists
    And non sequential record set observation for record set DWP1 101 003 101 exists


  @OccupationalPension
  Scenario: All indicators tells us it’s not a pension with relevant fields null or zero

    Given there is a citizen with one RTI record for a time period with all indicators set to false and all relevant fields equal to zero or null
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And RTI record 0 has the death benefit indicator is set to false
    And RTI record 0 has occupational pension flag set to false and all relevant fields equal to zero and null
    And RTI record 0 has FlexiblyAccessingPensionRightsIndicator is set to false
    And the response has 1 income streams
    And income stream 0 income item 0 has occupational pension flag set to false
    And the response has 1 observations
    And it is an all good data observation


  @OccupationalPension
  Scenario: All indicators tells us it’s not a pension with relevant field values

    Given there is a citizen with one RTI record for a time period with occupational pension indicator set to false and all relevant fields equal to values
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 1 RTI records
    And RTI record 0 has the death benefit indicator is set to false
    And RTI record 0 has occupational pension flag set to false and all relevant fields equal to 100 and 100
    And RTI record 0 has FlexiblyAccessingPensionRightsIndicator is set to false
    And the response has 1 income streams
    And income stream 0 income item 0 has occupational pension flag set to false
    And the response has 1 observations
    And it is an all good data observation