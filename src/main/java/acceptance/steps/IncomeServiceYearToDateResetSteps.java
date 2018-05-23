package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

@SuppressWarnings("squid:S00112")

public class IncomeServiceYearToDateResetSteps extends IncomeServiceSteps {
    private static final String YEAR_TO_DATE_RESET_CODE = "8";

    private static final String YEAR_TO_DATE_RESET_TIME_PERIOD_CODE = "7";

    private static final String YEAR_TO_DATE_RESET_OBSERVATION = "Year-to-date reset occurred at Payment : ";

    private static final String YEAR_TO_DATE_RESET_TIME_PERIOD_OBSERVATION = "Year-to-date reset occurred prior to supplied date range : ";

    private final APICall apiCall;

    public IncomeServiceYearToDateResetSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^there is a citizen with three RTI records for a time period with all relevant values equal$")
    public void setupRequest1() {
        apiCall.setRequestParams("QQ111113A?startdate=2015-08-31&enddate=2015-10-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Given("^there is a citizen with two RTI records for a time period with multiple year to date resets outside requested period$")
    public void setupRequest2() {
        apiCall.setRequestParams("QQ111113A?startdate=2015-12-31&enddate=2016-01-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with three RTI records for a time period with multiple year to date resets inside requested period$")
    public void setupRequest3() {
        apiCall.setRequestParams("QQ111113A?startdate=2015-10-31&enddate=2015-12-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with one RTI records for a time period which is first in the employment$")
    public void setupRequest4() {
        apiCall.setRequestParams("QQ111113A?startdate=2015-06-30&enddate=2015-08-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with three RTI records for a time period, first record within requested period and year to date reset has occurred$")
    public void setupRequest5() {
        apiCall.setRequestParams("QQ111113A?startdate=2015-11-30&enddate=2016-01-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }

    @Given("^there is a citizen with three RTI records for a time period and all relevant values equal except tax deducted!= Total tax to date$")
    public void setupRequest7() {
        apiCall.setRequestParams("QQ111113A?startdate=2017-04-01&enddate=2017-04-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with three RTI records for a time period and all relevant values equal except employees contributions != employees NI contribution to date$")
    public void setupRequest8() {
        apiCall.setRequestParams("QQ111113A?startdate=2018-04-01&enddate=2018-04-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with three RTI records for a time period and all relevant values equal except latest taxable pay to date <= previous taxable pay to date$")
    public void setupRequest9() {
        apiCall.setRequestParams("QQ111113A?startdate=2014-04-01&enddate=2014-04-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with eight RTI records for a time period across two tax years, multiple income streams, reset inside requested period$")
    public void setupRequest10() {
        apiCall.setRequestParams("QQ111114A?startdate=2015-02-28&enddate=2015-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }

    @Given("^there is a citizen with six RTI records for a time period across two tax years, multiple income streams, reset outside requested period$")
    public void setupRequest11() {
        apiCall.setRequestParams("QQ111114A?startdate=2017-03-31&enddate=2017-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Then("^year to date reset observation for unique payment id (\\d+) exists$")
    public void checkNonSequentialRecordObservationExists(String uniquePaymentId) {
        checkObservationCodeAndDescriptionExists(apiCall, YEAR_TO_DATE_RESET_CODE, YEAR_TO_DATE_RESET_OBSERVATION + uniquePaymentId, uniquePaymentId);
    }


    @Then("^year to date reset has occurred before requested time period observation for record set ([^\"]*) exists$")
    public void checkYearToDateResetTimePeriodObservationExists(String incomeStream) {
        checkObservationCodeAndDescriptionExists(apiCall, YEAR_TO_DATE_RESET_TIME_PERIOD_CODE, YEAR_TO_DATE_RESET_TIME_PERIOD_OBSERVATION + incomeStream, incomeStream);
    }


}