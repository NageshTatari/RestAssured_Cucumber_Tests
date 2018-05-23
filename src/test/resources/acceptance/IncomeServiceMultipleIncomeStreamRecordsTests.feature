Feature: Income service should identify records for multiple income streams

  As a calling system
  I want to receive income data separated by income where multiple income streams are present
  So that I can utilise this information in my business logic

  @MultipleIncomeStreamsRecords
  Scenario: Two employments

    Given there is a citizen with three RTI records for a time period where two have a common employer paye reference, hmrc office number, payroll id, nps number and third RTI record is distinct
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And RTI record 0 has an employer paye reference DWP332, hmrc office number 332, payroll id 332 and nps number 1
    And RTI record 1 has an employer paye reference DWP332, hmrc office number 332, payroll id 332 and nps number 1
    And RTI record 2 has an employer paye reference DWP332, hmrc office number 332, payroll id 332 and nps number 2
    And the response has 2 income streams
    And the response has 2 prepared income records in income stream 0
    And the response has 1 prepared income records in income stream 1
    And the response has 1 observations
    And it is an all good data observation


  @MultipleIncomeStreamsRecords
  Scenario: One mixed employment (earnings and pension), one normal

    Given there is a citizen with three RTI records for a time period, both with a common employer paye reference, hmrc office number, payroll id, nps number and second RTI record has occupational pension flag set to true
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And RTI record 0 has an employer paye reference DWP552, hmrc office number 552, payroll id 552 and nps number 3
    And RTI record 1 has an employer paye reference DWP552, hmrc office number 552, payroll id 552 and nps number 3
    And RTI record 2 has an employer paye reference DWP552, hmrc office number 552, payroll id 102 and nps number 3
    And the response has 2 income streams
    And the response has 1 prepared income records in income stream 0
    And the response has 2 prepared income records in income stream 1
    And the response has 1 observations
    And it is an all good data observation


  @MultipleIncomeStreamsRecords
  Scenario: Sub employment

    Given there is a citizen with three RTI records for a time period, two with a common employer paye reference, hmrc office number and different payroll id and nps number and second RTI record has occupational pension flag set to true
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And RTI record 0 has an employer paye reference DWP552, hmrc office number 552, payroll id 552 and nps number 5
    And RTI record 1 has an employer paye reference DWP552, hmrc office number 652, payroll id 552 and nps number 5
    And RTI record 2 has an employer paye reference DWP752, hmrc office number 552, payroll id 552 and nps number 5
    And the response has 3 income streams
    And the response has 1 prepared income records in income stream 0
    And the response has 1 prepared income records in income stream 1
    And the response has 1 prepared income records in income stream 2
    And the response has 1 observations
    And it is an all good data observation


  @MultipleIncomeStreamsRecords
  Scenario: Two employments, one has three income streams, one is sub employment, one of which is correctly marked as pension, other employment has two income streams, one of which is incorrectly marked as pension

    Given there is a citizen with five RTI records for a time period
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 5 RTI records
    And RTI record 0 has an employer paye reference DWP552, hmrc office number 552, payroll id 652 and nps number 6
    And RTI record 1 has an employer paye reference DWP552, hmrc office number 552, payroll id 552 and nps number 7
    And RTI record 2 has an employer paye reference DWP552, hmrc office number 552, payroll id 102 and nps number 8
    And RTI record 3 has an employer paye reference DWP662, hmrc office number 662, payroll id 662 and nps number 9
    And RTI record 4 has an employer paye reference DWP662, hmrc office number 662, payroll id 663 and nps number 10
    And the response has 5 income streams
    And the response has 1 prepared income records in income stream 0
    And the response has 1 prepared income records in income stream 1
    And the response has 1 prepared income records in income stream 2
    And the response has 1 prepared income records in income stream 3
    And the response has 1 prepared income records in income stream 4
    And the response has 1 observations
    And not an occupational pension record observation for unique payment id 666 exists