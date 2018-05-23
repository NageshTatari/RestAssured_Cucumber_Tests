package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import static org.hamcrest.Matchers.*;

@SuppressWarnings("all")
public class IncomeServiceDuplicateRecordsSteps extends IncomeServiceSteps {

    private static final String DUPLICATE_RECORD_CODE = "1";
    private static final String DUPLICATE_RECORD_REGEX = "Identical duplicate of payment ";


    private final APICall apiCall;

    public IncomeServiceDuplicateRecordsSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^There is a citizen with 2 RTI records with all fields identical and one distinct record$")
    public void setupRequest1() {
        apiCall.setRequestParams("QQ111301A?startdate=2015-04-01&enddate=2015-05-14&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^There is a citizen with 8 RTI records and one set of two duplicates with employer one and one set of five duplicates with employer two and one distinct record with employer three$")
    public void setupRequest2() {
        apiCall.setRequestParams("QQ111401A?startdate=2015-04-01&enddate=2015-06-26&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^There is a citizen with 4 RTI records where not all fields are identical$")
    public void setupRequest3() {
        apiCall.setRequestParams("QQ111501A?startdate=2015-04-01&enddate=2016-02-01&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Then("^the response has 2 identical RTI records with unique payment ID (\\d+)$")
    public void checkIDs0and2Identical(int upid) {
        apiCall.getResponse().then().body(getRTIRecordJsonPath(0) + UNIQUE_PAYMENT_ID, equalTo(upid));
        apiCall.getResponse().then().body(getRTIRecordJsonPath(2) + UNIQUE_PAYMENT_ID, equalTo(upid));
    }

    @Then("^one copy of the duplicate records is removed from prepared income$")
    public void checkOneDuplicateRemoved() {

        apiCall.getResponse().then().body(getIncomeItemsJsonPath(0), hasSize(2));

        int uPID1 = getPreparedRecordUniquePaymentId(apiCall, 0, 0);
        int uPID2 = getPreparedRecordUniquePaymentId(apiCall, 0, 1);
        Assert.assertTrue("Duplicate Unique Payment Id matching", uPID1 == 301 && uPID2 == 302);

    }


    @Then("^the response has 1 RTI record which is distinct with ID (\\d+)$")
    public void checkOneRecordNotDuplicated(int uniquePaymentID) {

        if (uniquePaymentID == 403) {
            int uPID1 = getRTIRecordUniquePaymentId(apiCall, 6);
            Assert.assertTrue("One records not matching uniquePaymentId", uPID1 == uniquePaymentID);
        }

        if (uniquePaymentID == 302) {
            int uPID1 = getRTIRecordUniquePaymentId(apiCall, 1);
            Assert.assertTrue("One records not matching uniquePaymentId", uPID1 == uniquePaymentID);
        }

    }


    @Then("^RTI record (\\d+) duplicate of record (\\d+) with id (\\d+)$")
    public void checkRtiRecordDuplicateOfRecordWithId(int record, int record2, int uniquePaymentId) {

        checkDuplicateRTIRecords(record, record2, uniquePaymentId);
    }


    @Then("^the response has 4 RTI records where not all fields are identical$")
    public void checkResponseNoDuplicates() {

        for (int i = 0; i < 4; i++) {
            int expected = 501 + i;
            int actual = getRTIRecordUniquePaymentId(apiCall, i);
            Assert.assertTrue("Record " + i + " does not have the expected uniquePaymentID", actual == expected);
        }
    }


    @Then("^all employers duplicate records is removed from prepared income$")
    public void checkAllDuplicatesRemovedForAllEmployers() {

        apiCall.getResponse().then().body(getIncomeStreamsJsonPath(), hasSize(3));

        int uPID1 = getPreparedRecordUniquePaymentId(apiCall, 0, 0);
        int uPID2 = getPreparedRecordUniquePaymentId(apiCall, 1, 0);
        int uPID3 = getPreparedRecordUniquePaymentId(apiCall, 2, 0);

        Assert.assertTrue("Unique Payment Id not matching", uPID1 == 101 && uPID2 == 201 && uPID3 == 301);

    }

    @Then("^observation for duplicate record removed relating to unique payment id (\\d+)$")
    public void checkObservationIsDuplicateRecord(String uniquePayID) {
        checkObservationCodeAndDescriptionExists(apiCall, DUPLICATE_RECORD_CODE, DUPLICATE_RECORD_REGEX + uniquePayID + " removed", uniquePayID);
    }


    private void checkDuplicateRTIRecords(int record, int record2, int uniquePaymentId) {

        int uPid1 = getRTIRecordUniquePaymentId(apiCall, record);
        int uPid2 = getRTIRecordUniquePaymentId(apiCall, record2);
        Assert.assertTrue("records are not duplicates", uPid1 == uniquePaymentId && uPid2 == uniquePaymentId);
    }

}
