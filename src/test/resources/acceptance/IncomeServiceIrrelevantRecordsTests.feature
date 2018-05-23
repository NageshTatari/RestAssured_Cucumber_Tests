Feature: Income service should remove records which are irrelevant for income

  As a calling system
  I do not want to receive income data for a citizen where irrelevant records identified
  So that I do not see records that will add no value when making calculations using income data


  @IrrelevantForIncome
  Scenario: All irrelevant

    Given There is a citizen with two RTI records for a time period with all irrelevant record fields null or zero
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And RTI record 0 has all irrelevant fields equal to zero or null
    And RTI record 1 has all irrelevant fields equal to null or zero
    And the response has 0 income streams
    And the response has 2 observations
    And irrelevant record observation for unique payment id 901 exists
    And irrelevant record observation for unique payment id 902 exists


  @IrrelevantForIncome
  Scenario: One irrelevant, one not

    Given There is a citizen with two RTI records for a time period where one record has all irrelevant RTI fields equal to null or zero
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And RTI record 0 has all irrelevant fields equal to null or zero
    And RTI record 1 has non-zero values for all irrelevant fields and taxable pay to date 100
    And the response has 1 income streams
    And the response has 1 prepared income records in income stream 0
    And the response has 1 observations
    And irrelevant record observation for unique payment id 902 exists


  @IrrelevantForIncome
  Scenario: Mixed set of relevant and not

    Given There is a citizen with three RTI records for a time period where two have all irrelevant record fields null or zero, separated by a record with values
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And RTI record 0 has all irrelevant fields equal to null
    And RTI record 1 has non-zero values for all irrelevant fields and taxable pay to date 200
    And RTI record 2 has all irrelevant fields equal to zero
    And the response has 1 income streams
    And the response has 1 prepared income records in income stream 0
    And the response has 2 observations
    And irrelevant record observation for unique payment id 904 exists
    And irrelevant record observation for unique payment id 906 exists


  @IrrelevantForIncome
  Scenario: Two employments, mixed set of relevant and not

    Given There is a citizen with four RTI records for a time period for two employments both have one irrelevant record
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 4 RTI records
    And RTI record 0 has all irrelevant fields equal to null
    And RTI record 1 has non-zero values for all irrelevant fields and taxable pay to date 100
    And RTI record 2 has all irrelevant fields equal to zero
    And RTI record 1 has non-zero values for all irrelevant fields and taxable pay to date 100
    And the response has 2 income streams
    And the response has 1 prepared income records in income stream 0
    And the response has 1 prepared income records in income stream 1
    And the response has 2 observations
    And irrelevant record observation for unique payment id 104 exists
    And irrelevant record observation for unique payment id 204 exists


  @IrrelevantForIncome
  Scenario: Some missing fields

    Given There is a citizen with two RTI records for a time period where one has optional irrelevant record field missing and other fields null or zero and one with optional irrelevant record field missing and other fields with values
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And RTI record 0 has an optional irrelevant RTI field missing and the other irrelevant RTI fields equal to null or zero
    And RTI record 1 has an optional irrelevant RTI field missing and the other irrelevant RTI fields with values
    And the response has 1 income streams
    And the response has 1 prepared income records in income stream 0
    And the response has 1 observations
    And irrelevant record observation for unique payment id 908 exists