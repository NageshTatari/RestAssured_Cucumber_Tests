Feature: Income service should identify records for a single income stream

  As a calling system
  I want to know that the records I have received in the response are for a single income stream
  So that I can use this information in my business logic


  @SingleIncomeStreamRecords
  Scenario: Single employment

    Given there is a citizen with three RTI records for a time period with a common employer paye reference, hmrc office number, payroll id and nps number
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And RTI record 0 has an employer paye reference DWP222, hmrc office number 222, payroll id 222 and nps number 11
    And RTI record 1 has an employer paye reference DWP222, hmrc office number 222, payroll id 222 and nps number 11
    And RTI record 2 has an employer paye reference DWP222, hmrc office number 222, payroll id 222 and nps number 11
    And the response has 1 income streams
    And the response has 3 prepared income records in income stream 0
    And the response has 1 observations
    And it is an all good data observation