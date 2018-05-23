package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.hamcrest.Matchers.equalTo;

@SuppressWarnings("all")
public class IncomeServiceOrderByPaymentDateAndUniquePaymentIdSteps extends IncomeServiceSteps {

    private final APICall apiCall;

    public IncomeServiceOrderByPaymentDateAndUniquePaymentIdSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^there is a citizen with eight RTI records for a time period, two employments with four records each, records are ordered by payment date and unique payment ID$")
    public void setupRequest1() {
        apiCall.setRequestParams("QQ332002A?startdate=2015-04-30&enddate=2015-09-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with seven RTI records for a time period, all records are related to single employment, records are ordered by payment date and unique payment ID$")
    public void setUpRequest2() {
        apiCall.setRequestParams("QQ222002A?startdate=2015-04-30&enddate=2015-08-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Then("^RTI record (\\d+) has an employer (\\d+) with payment date ([^\"]*) and unique payment id (\\d+)$")
    public void checkRtiRecordHasAnEmployerWithPaymentDateAndUniquePaymentId(int record, int employerNumber, String paymentDate, int uniquePaymentId) {
        checkSingleIncomeStreamPaymentRTIFields(record, paymentDate, uniquePaymentId);
    }


    private void checkSingleIncomeStreamPaymentRTIFields(int record,
                                                         String paymentDate,
                                                         int uniquePaymentId) {
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(record) + PAYMENT_DATE, equalTo(paymentDate))
                .body(getRTIRecordJsonPath(record) + UNIQUE_PAYMENT_ID, equalTo(uniquePaymentId));
    }

}
