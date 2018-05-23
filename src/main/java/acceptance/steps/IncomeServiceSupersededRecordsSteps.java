package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
public class IncomeServiceSupersededRecordsSteps extends IncomeServiceSteps {

    private static final String SUPERSEDES_RECORD_CODE = "9";

    private static final String LATE_PAYE_REPORTING_REASON_VALUE = "H";

    private static final String LATE_PAYE_REPORTING_REASON_DESCRIPTION = "Correction to earlier submission";


    private static final String SUPERSEDES_RECORD_REGEX = "Superseded record removed relating to Payment : ";

    private final APICall apiCall;

    public IncomeServiceSupersededRecordsSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^there is a citizen with two RTI records for a time period with same payment date and payment frequency$")
    public void setRequest1() {

        apiCall.setRequestParams("QQ111115A?startdate=2014-04-30&enddate=2014-04-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with two RTI records for a time period with same payment date and payment frequency and one of the record has H indicator$")
    public void setRequest8() {

        apiCall.setRequestParams("QQ111117A?startdate=2014-04-30&enddate=2014-04-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with two RTI records for a time period with same payment date and payment frequency and one of the record has A indicator$")
    public void setRequest9() {

        apiCall.setRequestParams("QQ111117A?startdate=2014-05-31&enddate=2014-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with two RTI records for a time period with same pay frequency and different payment date and one of the record has H indicator$")
    public void setRequest10() {

        apiCall.setRequestParams("QQ111117A?startdate=2014-06-30&enddate=2014-07-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with three RTI records for a time period with same payment date and payment frequency and second record has H indicator$")
    public void setRequest11() {

        apiCall.setRequestParams("QQ111117A?startdate=2014-08-31&enddate=2014-08-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Then("^superseded record observation for unique payment id (\\d+) exists$")
    public void checkNonSequentialRecordObservationExists(String uniquePaymentId) {
        checkObservationCodeAndDescriptionExists(apiCall, SUPERSEDES_RECORD_CODE, SUPERSEDES_RECORD_REGEX + uniquePaymentId, uniquePaymentId);
    }


    @Then("^income item (\\d+) has late paye reporting reason set to ([^\"]*)$")
    public void latePayeReportingRecordSetToH(int incomeItem, String indicator) {

        if ("H".equals(indicator)) {

            checkLatePayeReportingReasonValueAndDescription(apiCall, incomeItem, LATE_PAYE_REPORTING_REASON_VALUE, LATE_PAYE_REPORTING_REASON_DESCRIPTION);
        }

        if ("A".equals(indicator)) {

            checkLatePayeReportingReasonValueAndDescription(apiCall, incomeItem, "A", "Notional payment: Payment to Expat by third party or overseas employer");
        }

        if ("null".equals(indicator)) {

            checkLatePayeReportingReasonValueAndDescriptionFieldsAreNotPresent(apiCall, LATE_PAYE_REPORTING_REASON_DESCRIPTION);
        }

    }


    @Then("^observation for superseded record removed relating to unique payment id (\\d+)$")
    public void observationForUniquePaymentIdIsARemoveSupersededRecordObservation(String uniquePayId) {

        checkObservationCodeAndSupersededDescription(apiCall, SUPERSEDES_RECORD_CODE, SUPERSEDES_RECORD_REGEX + uniquePayId, uniquePayId);

    }


    @Given("^there is a citizen with three RTI records for a time period with same payment date and payment frequency$")
    public void setRequest2() {

        apiCall.setRequestParams("QQ111115A?startdate=2015-04-30&enddate=2015-04-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");

    }


    @Given("^there is a citizen with eight RTI records for a time period some with same payment date and payment frequency$")
    public void setRequest3() {
        apiCall.setRequestParams("QQ111115A?startdate=2016-04-14&enddate=2016-05-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with three RTI records for a time period with same payment date with different payment frequency$")

    public void setRequest4() {

        apiCall.setRequestParams("QQ111115A?startdate=2018-05-31&enddate=2018-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with four RTI records for a time period with multiple superseded records$")
    public void setRequest5() {

        apiCall.setRequestParams("QQ111116A?startdate=2016-02-28&enddate=2016-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with four RTI records for a time period with multiple employment with multiple superseded records$")
    public void setRequest6() {

        apiCall.setRequestParams("QQ111116A?startdate=2016-04-30&enddate=2017-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with four RTI records for a time period two with same payment date and payment frequency$")
    public void setRequest7() {

        apiCall.setRequestParams("QQ111115A?startdate=2017-04-30&enddate=2017-06-26&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    private void checkObservationCodeAndSupersededDescription(APICall apiCall, String code, String description, String identifier) {
        String responseBody = apiCall.getResponse().getBody().asString();
        Assert.assertTrue("Observation code not found in response json body ", responseBody.contains(code));
        Assert.assertTrue("Observation code not found in response json body ", responseBody.contains(identifier));
        Assert.assertTrue("Observation description not found in response json body ", responseBody.contains(description));
    }


    private void checkLatePayeReportingReasonValueAndDescription(APICall apiCall, int incomeItem, String value, String description) {


        apiCall.getResponse().then().body("preparedIncomeStreams.preparedIncome[" + incomeItem + "].latePayeReportingReason.any{it.containsValue('" + value + "')}", is(true));
        apiCall.getResponse().then().body("preparedIncomeStreams.preparedIncome[" + incomeItem + "].latePayeReportingReason.any{it.containsValue('" + description + "')}", is(true));


    }

    private void checkLatePayeReportingReasonValueAndDescriptionFieldsAreNotPresent(APICall apiCall, String description) {

        String responseBody = apiCall.getResponse().getBody().asString();

        Assert.assertFalse("data value not found in response json body ", responseBody.contains(description));


    }
}
