Feature: Income service should handle multiple conditions in the RTI data

  As a calling system
  I want to receive income data for a citizen where many different conditions appear in the RTI
  So that I can utilise this information in my business logic


  @Pipeline
  Scenario: Incorrect occupational pension flag, duplicates, irrelevant, remove repeat records, year to date reset and non-sequential and records are ordered

    Given There is a citizen with twelve RTI records for a time period for single employment
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 16 RTI records
    And RTI record 2 has occupational pension flag set to true and all relevant fields equal to 100 and null
    And RTI record 3 has all irrelevant fields equal to zero or null
    And RTI record 4 has occupational pension flag set to false and all relevant fields equal to zero's
    And RTI record 5 duplicate of record 4 with id 911
    And RTI record 6 non sequential record
    And RTI record 7 has an employer 1 with payment date 2015-01-30 and unique payment id 915
    And RTI record 8 has an employer 1 with payment date 2015-01-30 and unique payment id 914
    And RTI record 9 has an employer 1 with payment date 2015-02-27 and unique payment id 919
    And RTI record 10 has an employer 1 with payment date 2015-03-30 and unique payment id 916
    And RTI record 11 is a repeat record of RTI record 8 with unique payment id 950
    And the response has 1 income streams
    And income stream 0 has 11 income items
    And income stream 0 income item 0 has occupational pension flag set to false
    And income stream 0 income item 1 has occupational pension flag set to false
    And income stream 0 income item 2 non sequential record
    And the response has 12 observations
    And repeat record observation for unique payment id 950 exists
    And observation for duplicate record removed relating to unique payment id 911
    And irrelevant record observation for unique payment id 909 exists
    And not an occupational pension record observation for unique payment id 908 exists
    And superseded record observation for unique payment id 602 exists
    And superseded record observation for unique payment id 603 exists
    And non sequential record observation for unique payment id 911 exists
    And non sequential record observation for unique payment id 913 exists
    And non sequential record observation for unique payment id 904 exists
    And non sequential record set observation for record set DWP1 101 004 101 exists
    And year to date reset has occurred before requested time period observation for record set DWP1 101 004 101 exists
    And year to date reset observation for unique payment id 904 exists


  @Pipeline
  Scenario: Two employments,Incorrect occupational pension flag, duplicates, irrelevant, remove repeat records, year to date reset and non-sequential and records are ordered

    Given There is a citizen with thirteen RTI records for a time period for multiple employments
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 17 RTI records
    And RTI record 0 has occupational pension flag set to true and all relevant fields equal to 100 and null
    And RTI record 1 has all irrelevant fields equal to zero or null
    And RTI record 2 has occupational pension flag set to false and all relevant fields equal to zero's
    And RTI record 3 duplicate of record 2 with id 911
    And RTI record 4 non sequential record
    And RTI record 5 has an employer 1 with payment date 2015-01-30 and unique payment id 915
    And RTI record 6 has an employer 1 with payment date 2015-01-30 and unique payment id 914
    And RTI record 7 has an employer 1 with payment date 2015-02-27 and unique payment id 919
    And RTI record 8 has an employer 1 with payment date 2015-03-30 and unique payment id 916
    And RTI record 9 is a repeat record of RTI record 8 with unique payment id 970
    And RTI record 10 is a repeat record of RTI record 7 with unique payment id 960
    And the response has 2 income streams
    And income stream 0 has 3 income items
    And income stream 1 has 8 income items
    And income stream 0 income item 0 has occupational pension flag set to false
    And income stream 0 income item 1 has occupational pension flag set to false
    And income stream 0 income item 2 non sequential record
    And the response has 14 observations
    And repeat record observation for unique payment id 960 exists
    And repeat record observation for unique payment id 970 exists
    And observation for duplicate record removed relating to unique payment id 911
    And irrelevant record observation for unique payment id 909 exists
    And not an occupational pension record observation for unique payment id 908 exists
    And superseded record observation for unique payment id 602 exists
    And superseded record observation for unique payment id 603 exists
    And non sequential record observation for unique payment id 904 exists
    And non sequential record set observation for record set DWP222 222 222 101 exists
    And non sequential record observation for unique payment id 911 exists
    And non sequential record observation for unique payment id 913 exists
    And non sequential record set observation for record set DWP111 111 111 101 exists
    And year to date reset observation for unique payment id 904 exists
    And year to date reset has occurred before requested time period observation for record set DWP111 111 111 101 exists