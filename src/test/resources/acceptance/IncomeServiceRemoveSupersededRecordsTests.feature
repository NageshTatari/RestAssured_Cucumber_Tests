Feature: Income service should remove superseded records from the income service

  As a member of the RTI Disputes Team
  I want superseded records to be excluded from what I can see
  So that I have less "visual noise" on my screen.

  @SupersededRecords
  Scenario: One record is superseded

    Given there is a citizen with two RTI records for a time period with same payment date and payment frequency
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And the response has 1 income streams
    And income stream 0 has 1 income items
    And the response has 1 observations
    And observation for superseded record removed relating to unique payment id 102


  @SupersededRecords
  Scenario: Two records are superseded

    Given there is a citizen with three RTI records for a time period with same payment date and payment frequency
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And the response has 1 income streams
    And income stream 0 has 1 income items
    And the response has 1 observations
    And observation for superseded record removed relating to unique payment id 105


  @SupersededRecords
  Scenario: Multiple subsequent records are superseded

    Given there is a citizen with eight RTI records for a time period some with same payment date and payment frequency
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 8 RTI records
    And the response has 1 income streams
    And income stream 0 has 5 income items
    And the response has 3 observations
    And observation for superseded record removed relating to unique payment id 112
    And observation for superseded record removed relating to unique payment id 111
    And observation for superseded record removed relating to unique payment id 109


  @SupersededRecords
  Scenario: One superseded , subsequent records are not superseded

    Given there is a citizen with four RTI records for a time period two with same payment date and payment frequency
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 4 RTI records
    And the response has 1 income streams
    And income stream 0 has 3 income items
    And the response has 3 observations
    And superseded record observation for unique payment id 117 exists
    And non sequential record observation for unique payment id 116 exists
    And non sequential record set observation for record set DWP111 111 111 1 exists


  @SupersededRecords
  Scenario: Multiple payment frequency and superseded

    Given there is a citizen with three RTI records for a time period with same payment date with different payment frequency
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And the response has 1 income streams
    And income stream 0 has 2 income items
    And the response has 1 observations
    And observation for superseded record removed relating to unique payment id 121


  @SupersededRecords
  Scenario: Single employment, Multiple superseded records across two years

    Given there is a citizen with four RTI records for a time period with multiple superseded records
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 8 RTI records
    And the response has 1 income streams
    And income stream 0 has 4 income items
    And the response has 4 observations
    And observation for superseded record removed relating to unique payment id 134
    And observation for superseded record removed relating to unique payment id 143
    And observation for superseded record removed relating to unique payment id 132
    And observation for superseded record removed relating to unique payment id 142


  @SupersededRecords
  Scenario: Multiple employment, Multiple superseded records

    Given there is a citizen with four RTI records for a time period with multiple employment with multiple superseded records
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 8 RTI records
    And the response has 2 income streams
    And income stream 0 has 2 income items
    And income stream 1 has 2 income items
    And the response has 4 observations
    And observation for superseded record removed relating to unique payment id 143
    And observation for superseded record removed relating to unique payment id 152
    And observation for superseded record removed relating to unique payment id 142
    And observation for superseded record removed relating to unique payment id 153


  @SupersededRecords
  Scenario: One record is superseded with late paye reporting reason set to H

    Given there is a citizen with two RTI records for a time period with same payment date and payment frequency and one of the record has H indicator
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And the response has 1 income streams
    And income stream 0 has 1 income items
    And income item 0 has late paye reporting reason set to H
    And the response has 1 observations
    And observation for superseded record removed relating to unique payment id 131


  @SupersededRecords
  Scenario: One record is superseded with late paye reporting reason set to A

    Given there is a citizen with two RTI records for a time period with same payment date and payment frequency and one of the record has A indicator
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And the response has 1 income streams
    And income stream 0 has 1 income items
    And income item 0 has late paye reporting reason set to A
    And the response has 1 observations
    And observation for superseded record removed relating to unique payment id 133


  @SupersededRecords
  Scenario: No records are superseded with different payment date and late paye reporting reason set to H

    Given there is a citizen with two RTI records for a time period with same pay frequency and different payment date and one of the record has H indicator
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And the response has 1 income streams
    And income stream 0 has 2 income items
    And income item 0 has late paye reporting reason set to H
    And the response has 1 observations
    And it is an all good data observation


  @SupersededRecords
  Scenario: Two records are superseded with same payment date and payment frequency and second record has H indicator

    Given there is a citizen with three RTI records for a time period with same payment date and payment frequency and second record has H indicator
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And the response has 1 income streams
    And income stream 0 has 1 income items
    And income item 0 has late paye reporting reason set to null
    And the response has 1 observations
    And observation for superseded record removed relating to unique payment id 138