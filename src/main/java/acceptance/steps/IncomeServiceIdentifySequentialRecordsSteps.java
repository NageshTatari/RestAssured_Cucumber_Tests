package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import java.math.BigDecimal;

@SuppressWarnings("all")
public class IncomeServiceIdentifySequentialRecordsSteps extends IncomeServiceSteps {
    private static final String NON_SEQUENTIAL_RECORD_CODE = "2";
    private static final String NON_SEQUENTIAL_RECORD_SET_CODE = "3";

    private static final String NON_SEQUENTIAL_RECORD_REGEX = "Taxable Pay and Taxable Pay YTD are not sequential for Unique Payment ID : ";
    private static final String NON_SEQUENTIAL_RECORD_SET_REGEX = "Taxable Pay and Taxable Pay YTD not fully sequential for income stream ID : ";

    private final APICall apiCall;

    public IncomeServiceIdentifySequentialRecordsSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }


    @Given("^there is a citizen with 2 RTI records for a time period and the first record is sequential$")
    public void setupRequest01() {
        apiCall.setRequestParams("QQ111006A?startdate=2015-05-31&enddate=2015-06-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Given("^there is a citizen with 2 RTI records for a time period and the first record is non sequential$")
    public void setupRequest02() {
        apiCall.setRequestParams("QQ111006A?startdate=2015-07-31&enddate=2015-08-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Given("^there is a citizen with 2 RTI records for a time period and the first record is non sequential compared to previous missing record$")
    public void setupRequest03() {
        apiCall.setRequestParams("QQ111006A?startdate=2015-10-31&enddate=2015-11-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Given("^there is a citizen with 2 RTI records for a time period and the first record is sequential compared to previous two missing records$")
    public void setupRequest04() {
        apiCall.setRequestParams("QQ111006A?startdate=2016-08-31&enddate=2016-09-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Given("^there is a citizen with 1 RTI records for a time period and the first record is non sequential$")
    public void setupRequest05() {
        apiCall.setRequestParams("QQ111006A?startdate=2015-12-31&enddate=2016-02-28&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Given("^there is a citizen with 3 RTI records for a time period and the first and third records are non sequential$")
    public void setupRequest06() {
        apiCall.setRequestParams("QQ111006A?startdate=2016-03-31&enddate=2016-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Given("^there is a citizen with 3 RTI records for a time period and all records are sequential$")
    public void setupRequest07() {
        apiCall.setRequestParams("QQ111006A?startdate=2014-03-31&enddate=2014-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Given("^there is a citizen with RTI records for a time period$")
    public void setupRequest08() {
        apiCall.setRequestParams("QQ111006A?startdate=2017-03-31&enddate=2017-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Then("^non sequential record observation for unique payment id (\\d+) exists$")
    public void checkNonSequentialRecordObservationExists(String uniquePaymentId) {
        checkObservationCodeAndDescriptionExists(apiCall, NON_SEQUENTIAL_RECORD_CODE, NON_SEQUENTIAL_RECORD_REGEX + uniquePaymentId, uniquePaymentId);
    }


    @Then("^non sequential record set observation for record set ([^\"]*) exists$")
    public void checkNonSequentialRecordSetObservationExists(String recordSet) {
        checkObservationCodeAndDescriptionExists(apiCall, NON_SEQUENTIAL_RECORD_SET_CODE, NON_SEQUENTIAL_RECORD_SET_REGEX + recordSet, recordSet);
    }

    @Then("^income stream (\\d+) income item (\\d+) is sequential record$")
    public void checkIncomeItemSequential(int stream, int item) {
        if (item != 0) {

            BigDecimal currentTaxablePay = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item) + TAXABLE_PAY).toString());
            BigDecimal currentTaxablePayToDate = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item) + TAXABLE_PAY_TO_DATE).toString());
            BigDecimal previousTaxablePayToDate = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item - 1) + TAXABLE_PAY_TO_DATE).toString());

            Assert.assertTrue("Income items pay and pay-to-date are not in sequence", previousTaxablePayToDate.add(currentTaxablePay).compareTo(currentTaxablePayToDate) == 0);
        }
    }


    @Then("^income stream (\\d+) income item (\\d+) is ([^\"]*) record of tax year and is sequential record$")
    public void checkIncomeItemSequentialForFirstRecordOfTaxYear(int stream, int item, String firstRecord) {
        if ("second".equals(firstRecord)) {

            BigDecimal currentTaxablePay = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item) + TAXABLE_PAY).toString());
            BigDecimal currentTaxablePayToDate = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item) + TAXABLE_PAY_TO_DATE).toString());
            BigDecimal previousTaxablePayToDate = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item - 1) + TAXABLE_PAY_TO_DATE).toString());

            Assert.assertTrue("Income items pay and pay-to-date are not in sequence", previousTaxablePayToDate.add(currentTaxablePay).compareTo(currentTaxablePayToDate) == 0);
        }
    }

    @Then("^income stream (\\d+) income item (\\d+) is non sequential record$")
    public void checkIncomeItemNonSequential(int stream, int item) {
        if (item != 0) {

            BigDecimal currentTaxablePay = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item) + TAXABLE_PAY).toString());
            BigDecimal currentTaxablePayToDate = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item) + TAXABLE_PAY_TO_DATE).toString());
            BigDecimal previousTaxablePayToDate = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item - 1) + TAXABLE_PAY_TO_DATE).toString());

            Assert.assertTrue("Income items pay and pay-to-date are in sequence", previousTaxablePayToDate.add(currentTaxablePay).compareTo(currentTaxablePayToDate) != 0);
        }
    }


    @Then("^RTI record (\\d+) has taxable pay to date (\\d+) and ID (\\d+)$")
    public void checkRtiRecordTaxablePayToDateAndID(int record, int taxablePayToDate, int uniquePaymentId) {
        checkRTIRecordUniquePaymentId(apiCall, record, uniquePaymentId);
        checkRTIRecordTaxablePayToDate(apiCall, record, taxablePayToDate);
    }

    @Then("^RTI record (\\d+) has taxable pay to date (\\d+) and taxable pay (\\d+) and ID (\\d+)$")
    public void checkRtiRecordTaxablePayToDateTaxablePayAndID(int record, int taxablePayToDate, int taxablePay, int uniquePaymentId) {
        checkRTIRecordUniquePaymentId(apiCall, record, uniquePaymentId);
        checkRTIRecordTaxablePayToDate(apiCall, record, taxablePayToDate);
        checkRTIRecordTaxablePay(apiCall, record, taxablePay);
    }

}
