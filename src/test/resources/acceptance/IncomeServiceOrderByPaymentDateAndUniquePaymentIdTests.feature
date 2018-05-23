Feature: Income service should supply prepared income data in a defined order

  As a calling system
  I want to receive income data that is in a predictable order
  So that I can utilise this information in my business logic without having to order it myself

  @OrderByPaymentDateAndUniquePaymentId
  Scenario: Multiple employment, order by payment date and unique payment id

    Given there is a citizen with eight RTI records for a time period, two employments with four records each, records are ordered by payment date and unique payment ID
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 8 RTI records
    And RTI record 0 has an employer 1 with payment date 2015-04-30 and unique payment id 303
    And RTI record 1 has an employer 1 with payment date 2015-06-30 and unique payment id 305
    And RTI record 2 has an employer 1 with payment date 2015-05-30 and unique payment id 301
    And RTI record 3 has an employer 1 with payment date 2015-06-30 and unique payment id 304
    And RTI record 4 has an employer 2 with payment date 2015-07-30 and unique payment id 503
    And RTI record 5 has an employer 2 with payment date 2015-07-30 and unique payment id 501
    And RTI record 6 has an employer 2 with payment date 2015-09-30 and unique payment id 504
    And RTI record 7 has an employer 2 with payment date 2015-08-30 and unique payment id 506
    And the response has 2 income streams
    And income stream 0 is ordered by payment date and unique payment id
    And income stream 1 is ordered by payment date and unique payment id
    And the response has 4 prepared income records in income stream 0
    And the response has 4 prepared income records in income stream 1
    And the response has 1 observations
    And it is an all good data observation


  @OrderByPaymentDateAndUniquePaymentId
  Scenario: Single employment, order by payment date and unique payment id

    Given there is a citizen with seven RTI records for a time period, all records are related to single employment, records are ordered by payment date and unique payment ID
    When I request income data for that citizen and time period
    Then a successful response is received
    And the response has 7 RTI records
    And RTI record 0 has an employer 1 with payment date 2015-04-30 and unique payment id 902
    And RTI record 1 has an employer 1 with payment date 2015-06-30 and unique payment id 905
    And RTI record 2 has an employer 1 with payment date 2015-08-30 and unique payment id 910
    And RTI record 2 non sequential record
    And RTI record 3 has an employer 1 with payment date 2015-05-30 and unique payment id 901
    And RTI record 4 has an employer 1 with payment date 2015-07-30 and unique payment id 908
    And RTI record 5 has an employer 1 with payment date 2015-07-30 and unique payment id 909
    And RTI record 6 has an employer 1 with payment date 2015-08-30 and unique payment id 911
    And income stream 0 is ordered by payment date and unique payment id
    And the response has 7 prepared income records in income stream 0
    And the response has 1 observations
    And it is an all good data observation