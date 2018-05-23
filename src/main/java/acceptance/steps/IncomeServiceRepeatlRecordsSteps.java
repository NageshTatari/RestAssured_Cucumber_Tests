package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SuppressWarnings("all")
public class IncomeServiceRepeatlRecordsSteps extends IncomeServiceSteps {
    private static final String REPEAT_RECORD_CODE = "6";

    private static final String REPEAT_RECORD_REGEX = " removed as repeat.";

    private final APICall apiCall;

    public IncomeServiceRepeatlRecordsSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^there is a citizen with 2 RTI records for a time period with all fields identical apart from unique payment id$")
    public void setupRequest1() {
        apiCall.setRequestParams("QQ111007A?startdate=2015-04-01&enddate=2015-04-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Then("^the repeat record is removed from income stream (\\d+)$")
    public void checkRepeatRecordRemoved(int incomeStream) {

        apiCall.getResponse().then().body(getIncomeItemsJsonPath(incomeStream), hasSize(1));
    }


    @Given("^there is a citizen with 3 RTI records for a time period with different unique pay id and all fields identical$")
    public void setupRequest2() {
        apiCall.setRequestParams("QQ111007A?startdate=2015-05-01&enddate=2015-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Then("^the repeat records are removed from income stream (\\d+)$")
    public void checkTheRepeatRecordsRemoved(int incomeStream) {

        apiCall.getResponse().then().body(getIncomeItemsJsonPath(incomeStream), hasSize(1));
    }


    @Then("^repeat record observation for unique payment id (\\d+) exists$")
    public void checkRepeatRecordObservationExists(String uniquePayID) {
        checkObservationCodeAndDescriptionExists(apiCall, REPEAT_RECORD_CODE, "Payment ID " + uniquePayID + REPEAT_RECORD_REGEX, uniquePayID);
    }

    @Then("^RTI record (\\d+) is a repeat record of RTI record (\\d+) with unique payment id (\\d+)$")
    public void checkRepeatRecord(int repeatRecord, String record, int uniquePayID) {

        apiCall.getResponse().then().body(getRTIRecordJsonPath(repeatRecord) + UNIQUE_PAYMENT_ID, equalTo(uniquePayID));
    }

    @Given("^there is a citizen with four RTI records for a time period with different unique pay id and two income streams each with 2 records with all fields identical$")
    public void setupRequest3() {
        apiCall.setRequestParams("QQ111007A?startdate=2015-06-01&enddate=2015-07-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


}
