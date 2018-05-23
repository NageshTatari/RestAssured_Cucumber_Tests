Feature: Income service should identify sequential records for employments

  As a calling system
  I want to know that the records I have received in response are in sequence
  So that I can use this information in my business logic


  @SequentialRecords
  Scenario: first record is sequential (no previous RTI records exist)

    Given there is a citizen with 2 RTI records for a time period and the first record is sequential
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And the response has 1 income streams
    And income stream 0 has 2 income items
    And income stream 0 income item 0 is sequential record
    And income stream 0 income item 1 is sequential record
    And the response has 1 observations
    And it is an all good data observation


  @SequentialRecords
  Scenario:first record is non sequential

    Given there is a citizen with 2 RTI records for a time period and the first record is non sequential
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And the response has 1 income streams
    And income stream 0 has 2 income items
    And income stream 0 income item 0 is non sequential record
    And income stream 0 income item 1 is sequential record
    And the response has 2 observations
    And non sequential record observation for unique payment id 903 exists
    And non sequential record set observation for record set DWP1 101 101 101 exists


  @SequentialRecords
  Scenario:first record is non sequential (when compared to previous missing record prior to date parameters specified)

    Given there is a citizen with 2 RTI records for a time period and the first record is non sequential compared to previous missing record
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And the response has 1 income streams
    And income stream 0 has 2 income items
    And income stream 0 income item 0 is non sequential record
    And income stream 0 income item 1 is sequential record
    And the response has 2 observations
    And non sequential record observation for unique payment id 905 exists
    And non sequential record set observation for record set DWP1 101 101 101 exists


  @SequentialRecords
  Scenario:first record is sequential (when compared to previous two missing records prior to date parameters specified)

    Given there is a citizen with 2 RTI records for a time period and the first record is sequential compared to previous two missing records
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 2 RTI records
    And the response has 1 income streams
    And income stream 0 has 2 income items
    And income stream 0 income item 0 is sequential record
    And income stream 0 income item 1 is sequential record
    And the response has 1 observations
    And it is an all good data observation


  @SequentialRecords
  Scenario:only one record returned in requested period identified as non sequential (when compared to previous record prior to date parameters specified)

    Given there is a citizen with 1 RTI records for a time period and the first record is non sequential
    When I request income data for that citizen and time period
    And the response has 1 RTI records
    And the response has 1 income streams
    And income stream 0 has 1 income items
    And income stream 0 income item 0 is non sequential record
    And the response has 2 observations
    And non sequential record observation for unique payment id 907 exists
    And non sequential record set observation for record set DWP1 101 101 101 exists


  @SequentialRecords
  Scenario:first and third records are non sequential (across two tax years)

    Given there is a citizen with 3 RTI records for a time period and the first and third records are non sequential
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And the response has 1 income streams
    And income stream 0 has 3 income items
    And income stream 0 income item 0 is non sequential record
    And income stream 0 income item 1 is first record of tax year and is sequential record
    And income stream 0 income item 2 is non sequential record
    And the response has 3 observations
    And non sequential record observation for unique payment id 908 exists
    And non sequential record observation for unique payment id 910 exists
    And non sequential record set observation for record set DWP1 101 101 101 exists


  @SequentialRecords
  Scenario:all records are sequential (across two tax years)

    Given there is a citizen with 3 RTI records for a time period and all records are sequential
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 3 RTI records
    And the response has 1 income streams
    And income stream 0 has 3 income items
    And income stream 0 income item 0 is sequential record
    And income stream 0 income item 1 is first record of tax year and is sequential record
    And income stream 0 income item 2 is sequential record
    And the response has 1 observations
    And it is an all good data observation


  @SequentialRecords
  Scenario:main employment, sub employment and occupational pension across two tax years

    Given there is a citizen with RTI records for a time period
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 9 RTI records
    And the response has 3 income streams
    And income stream 0 has 3 income items
    And income stream 1 has 3 income items
    And income stream 2 has 3 income items
    And income stream 0 income item 0 is sequential record
    And income stream 0 income item 1 is first record of tax year and is sequential record
    And income stream 0 income item 2 is non sequential record
    And income stream 1 income item 0 is sequential record
    And income stream 1 income item 1 is first record of tax year and is sequential record
    And income stream 1 income item 2 is non sequential record
    And income stream 2 income item 0 is sequential record
    And income stream 2 income item 1 is first record of tax year and is sequential record
    And income stream 2 income item 2 is non sequential record
    And the response has 6 observations
    And non sequential record observation for unique payment id 920 exists
    And non sequential record set observation for record set DWP101 101 101 102 exists
    And non sequential record observation for unique payment id 923 exists
    And non sequential record set observation for record set DWP101 101 103 101 exists
    And non sequential record observation for unique payment id 916 exists
    And non sequential record set observation for record set DWP101 101 101 101 exists