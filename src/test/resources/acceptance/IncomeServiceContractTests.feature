Feature: Income service contract tests

  As a calling system
  I want to verify income service contract information
  So that I can ensure that the contract is met


  @IncomeServiceContractTests
  Scenario: Income service successful response

    Given valid request header parameters
    And query parameter start date is in the format 2015-04-30
    And query parameter end date is in the format 2015-06-30
    And start date is prior to or equal to end date
    And NINO format = "QQ111001A"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "200"
    And response header parameter Cache-Control = "no-cache, no-store"
    And response header parameter Content-Type = "application/json"
    And response body contains agreed headers with data fields


  @IncomeServiceContractTests
  Scenario: Start and end dates are the same

    Given query parameter start date is in the format 2015-04-30
    And query parameter end date is in the format 2015-04-30
    And Start date is equal to end date
    And NINO format = "QQ111001A"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "200"
    And response header parameter Cache-Control = "no-cache, no-store"
    And response header parameter Content-Type = "application/json"
    And response body contains agreed headers with data fields


  @IncomeServiceContractTests
  Scenario: Test NINO supplied all validation rules are met, no data to return

    Given query parameter start date is in the format 2015-04-01
    And query parameter end date is in the format 2015-04-20
    And start date is prior to or equal to end date
    And NINO format = "QQ111001A"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "200"
    And response header parameter Cache-Control = "no-cache, no-store"
    And response header parameter Content-Type = "application/json"
    And response body contains agreed headers with no data fields


  @IncomeServiceContractTests
  Scenario Outline: NINO is not in correct format

    Given query parameter start date is in the format 2015-04-30
    And query parameter end date is in the format 2015-06-30
    And start date is prior to or equal to end date
    And NINO format incorrect "<nino>"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "400"
    And response body contains error message nino must match and "BAD_REQUEST"
    And response header parameter Content-Type = "application/json"

    Examples:
      | nino       |
      | A000000C   |
      | 000000A    |
      | 000000000  |
      | AB000000CD |
      | SA00000B   |
      | ABCEGHJKL  |
      | AA00000    |


  @IncomeServiceContractTests
  Scenario Outline: Invalid nino's check

    Given query parameter start date is in the format 2015-04-30
    And query parameter end date is in the format 2015-06-30
    And start date is prior to or equal to end date
    And Invalid nino "<nino>"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "400"
    And response body contains error message nino must match and "BAD_REQUEST"
    And response header parameter Content-Type = "application/json"

    Examples:
      | nino      |
      | AD123456C |
      | DA123456C |
      | FB123456A |
      | BF123456A |
      | CI123456B |
      | IC123456B |
      | UG123456D |
      | GU123456D |
      | MV123456A |
      | VM123456A |
      | AO123456C |
      | BG123456A |
      | GB123456B |
      | NK123456C |
      | KN123456D |
      | TN123456A |
      | NT123456B |
      | ZZ123456C |
      | AB123456E |
      | BC123456I |
      | GK123456P |
      | ZS123456V |
      | WA123456Z |


  @IncomeServiceContractTests
  Scenario Outline: start date is not in correct format

    Given start date format incorrect <sDate>
    And query parameter end date is in the format <eDate>
    And NINO format = "QQ111001A"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "400"
    And response body contains error message "<emessage>" and "BAD_REQUEST"
    And response header parameter Content-Type = "application/json"

    Examples:
      | sDate      | eDate      | emessage                                                |
      | 2015/04/30 | 2015-06-30 | Text '2015/04/30' could not be parsed at index 4        |
      | 2015.04.30 | 2015-06-30 | Text '2015.04.30' could not be parsed at index 4        |
      | 30-04-2015 | 2015-06-30 | Text '30-04-2015' could not be parsed at index 0        |
      | 04-30-2015 | 2015-06-30 | Text '04-30-2015' could not be parsed at index 0        |
      | 2015-04-31 | 2015-05-31 | Invalid date 'APRIL 31'                                 |
      | 2015-02-29 | 2015-05-31 | Invalid date 'February 29' as '2015' is not a leap year |


  @IncomeServiceContractTests
  Scenario Outline: end date is not in correct format

    Given query parameter start date is in the format <sDate>
    And end date format incorrect <eDate>
    And NINO format = "QQ111001A"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "400"
    And response body contains error message "<emessage>" and "BAD_REQUEST"
    And response header parameter Content-Type = "application/json"

    Examples:
      | sDate      | eDate      | emessage                                                |
      | 2015-04-30 | 2015/06/30 | Text '2015/06/30' could not be parsed at index 4        |
      | 2015-04-30 | 2015.06.30 | Text '2015.06.30' could not be parsed at index 4        |
      | 2015-04-30 | 30-06-2015 | Text '30-06-2015' could not be parsed at index 0        |
      | 2015-04-30 | 06-30-2015 | Text '06-30-2015' could not be parsed at index 0        |
      | 2015-04-01 | 2015-04-31 | Invalid date 'APRIL 31'                                 |
      | 2015-01-29 | 2015-02-29 | Invalid date 'February 29' as '2015' is not a leap year |


  @IncomeServiceContractTests
  Scenario: start date is not supplied

    Given query parameter end date is in the format 2015-06-30
    And start date not supplied
    And NINO format = "QQ111001A"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "400"
    And response body contains error message "startDate must not be null" and "BAD_REQUEST"
    And response header parameter Content-Type = "application/json"


  @IncomeServiceContractTests
  Scenario: end date is not supplied

    Given query parameter start date is in the format 2015-04-30
    And end date not supplied
    And NINO format = "QQ111001A"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "400"
    And response body contains error message "endDate must not be null" and "BAD_REQUEST"
    And response header parameter Content-Type = "application/json"


  @IncomeServiceContractTests
  Scenario: Start date is after end date

    Given query parameter start date is in the format 2015-06-30
    And query parameter end date is in the format 2015-04-30
    And Start date > end date
    And NINO format = "QQ111001A"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "400"
    And response body contains error message "Start date is after end date" and "BAD_REQUEST"
    And response header parameter Content-Type = "application/json"


  @IncomeServiceContractTests
  Scenario: NINO does not exist

    Given query parameter start date is in the format 2015-04-30
    And query parameter end date is in the format 2015-06-30
    And start date is prior to or equal to end date
    And NINO format = "OA404404A"
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "404"
    And response body contains error message "NINO not found" and "NOT_FOUND"
    And response header parameter Content-Type = "application/json"


  @IncomeServiceContractTests
  Scenario: NINO is not supplied

    Given query parameter start date is in the format 2015-04-30
    And query parameter end date is in the format 2015-06-30
    And NINO format = ""
    And first name = John
    And middle name = Bob
    And last name = Dixon
    And initials = JB
    And sex = M
    And date of birth = 1990-06-01
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "404"
    And response body contains error message "/gcii/income/" and "NOT_FOUND"
    And response header parameter Content-Type = "application/json"


  @IncomeServiceContractTests
  Scenario: Incorrect request method

    Given valid query parameters
    And request header parameter Request Method = "PUT"
    When a call is made to the income service with incorrect request method
    Then a response is received with the status code "405"
    And response body contains error message "Request method 'PUT' not supported" and "METHOD_NOT_ALLOWED"
    And response header parameter Content-Type = "application/json"


  @IncomeServiceContractTests
  Scenario: Incorrect request header parameters supplied

    Given valid query parameters
    And request header parameter Accept = "application/xml"
    And request header parameter Accept-Encoding = "compress"
    When a call is made to the income service with incorrect request headers
    Then a response is received with the status code "406"
    And response body contains error message "Could not find acceptable representation" and "NOT_ACCEPTABLE"
    And response header parameter Content-Type = "application/json"


  @IncomeServiceContractTests
  Scenario: RTI service Unavailable

    Given There is a citizen with NINO QQ503503A and RTI data for the period between 2015-04-30 and 2015-04-30 dates
    When a call is made to the income service
    Then a response is received with the status code "503"
    And response header parameter Content-Type = "application/json"


  @IncomeServiceContractTests
  Scenario Outline: A call is made to income service with valid parameters

    Given query parameter start date is in the format 2015-04-30
    And query parameter end date is in the format 2015-04-30
    And NINO format = "QQ111001A"
    And first name = <forename>
    And middle name = <secondForename>
    And last name = <surname>
    And initials = <initials>
    And sex = <sex>
    And date of birth = <dob>
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "200"

    Examples:
      | forename | secondForename | surname | initials | dob        | sex |
      | John     | Bob            | Dixon   | JB       | 1990-06-01 | M   |
      |          | Bob            | Dixon   | JB       | 1990-06-01 | M   |
      | John     |                | Dixon   | JB       | 1990-06-01 | M   |
      | John     | Bob            | Dixon   |          | 1990-06-01 | M   |
      | John     | Bob            | Dixon   | JB       |            | M   |
      | John     | Bob            | Dixon   | JB       | 1990-06-01 |     |
      |          |                | Dixon   |          |            |     |


  @IncomeServiceContractTests
  Scenario Outline: A call is made to income service with invalid parameters

    Given query parameter start date is in the format 2015-04-30
    And query parameter end date is in the format 2015-04-30
    And NINO format = "QQ111001A"
    And first name = <forename>
    And middle name = <secondForename>
    And last name = <surname>
    And initials = <initials>
    And date of birth = <dob>
    And sex = <sex>
    When a call is made to the income service for that NINO and query parameters
    Then a response is received with the status code "400"
    And response body contains error message "<emessage>" and "<statusCode>"

    Examples:
      | forename | secondForename | surname | initials | dob        | sex | emessage                                                | statusCode  |
      | John     | Bob            |         | JB       | 1990-06-01 | M   | lastName must not be blank                              | BAD_REQUEST |
      | John     | Bob            | Dixon   | JB       | 1990-20-01 | M   | Invalid value for MonthOfYear (valid values 1 - 12): 20 | BAD_REQUEST |
      | John     | Bob            | Dixon   | JB       | 10-10-1990 | M   | Text '10-10-1990' could not be parsed at index 0        | BAD_REQUEST |
      | John     | Bob            | Dixon   | JB       | 1990/10/10 | M   | Text '1990/10/10' could not be parsed at index 4        | BAD_REQUEST |