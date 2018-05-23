package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.hamcrest.Matchers.equalTo;


public class IncomeServiceSingleIncomeStreamRecordsSteps extends IncomeServiceSteps {

    private final APICall apiCall;

    public IncomeServiceSingleIncomeStreamRecordsSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^there is a citizen with three RTI records for a time period with a common employer paye reference, hmrc office number, payroll id and nps number$")
    public void setUpRequest1() {
        apiCall.setRequestParams("QQ222001A?startdate=2015-05-31&enddate=2015-07-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Then("^RTI record (\\d+) has an employer paye reference ([^\"]*), hmrc office number (\\d+), payroll id (\\d+) and nps number (\\d+)$")
    public void checkRTIRecordHasAnEmployerPayeReferenceHmrcOfficeNumberPayrollIdentifierAndNPSNumber(int record, String employerPayeRef, String hmrcOfficeNo, String payrollIdentifier, int npsNumber) {

        checkSingleIncomeStreamRTIFields(record, employerPayeRef, hmrcOfficeNo, payrollIdentifier, npsNumber);
    }


    private void checkSingleIncomeStreamRTIFields(int record,
                                                  String employerPayeReference,
                                                  String hmrcOfficeNumber,
                                                  String payrollId,
                                                  int npsNumber) {
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(record) + HMRC_OFFICE_NUMBER, equalTo(hmrcOfficeNumber))
                .body(getRTIRecordJsonPath(record) + EMPLOYER_PAYE_REFERENCE, equalTo(employerPayeReference))
                .body(getRTIRecordJsonPath(record) + PAYROLL_ID, equalTo(payrollId))
                .body(getRTIRecordJsonPath(record) + NPS_NUMBER, equalTo(npsNumber));
    }


}
