package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.math.BigDecimal;

import static acceptance.helpers.BigDecimalHelper.equalToValue;

public class IncomeServiceIrrelevantRecordsSteps extends IncomeServiceSteps {

    private static final String IRRELEVANT_RECORD_CODE = "11";
    private static final String IRRELEVANT_RECORD_REGEX = " removed as it is irrelevant to income calculations";

    private final APICall apiCall;

    public IncomeServiceIrrelevantRecordsSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^There is a citizen with two RTI records for a time period with all irrelevant record fields null or zero$")
    public void setUpRequest1() {
        apiCall.setRequestParams("QQ111101A?startdate=2015-04-30&enddate=2015-05-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^There is a citizen with two RTI records for a time period where one record has all irrelevant RTI fields equal to null or zero$")
    public void setUpRequest2() {
        apiCall.setRequestParams("QQ111101A?startdate=2015-05-30&enddate=2015-06-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^There is a citizen with three RTI records for a time period where two have all irrelevant record fields null or zero, separated by a record with values$")
    public void setUpRequest3() {
        apiCall.setRequestParams("QQ111101A?startdate=2015-07-31&enddate=2015-09-30&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^There is a citizen with two RTI records for a time period where one has optional irrelevant record field missing and other fields null or zero and one with optional irrelevant record field missing and other fields with values$")
    public void setUpRequest4() {
        apiCall.setRequestParams("QQ111101A?startdate=2015-11-30&enddate=2015-12-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^There is a citizen with four RTI records for a time period for two employments both have one irrelevant record$")
    public void setUpRequest5() {
        apiCall.setRequestParams("QQ111101A?startdate=2014-01-30&enddate=2014-04-05&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Then("^RTI record (\\d+) has all irrelevant fields equal to zero or null$")
    public void checkRTIRecordIrrelevantFieldsZeroOrNull(int record) {
        checkIrrelevantRTIFields(record, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null, BigDecimal.ZERO, BigDecimal.ZERO, null);
    }

    @Then("^RTI record (\\d+) has all irrelevant fields equal to null or zero$")
    public void checkRTIRecordIrrelevantFieldsNullOrZero(int record) {
        checkIrrelevantRTIFields(record, null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Then("^RTI record (\\d+) has non-zero values for all irrelevant fields and taxable pay to date (\\d+)$")
    public void checkRTIRecordIrrelevantFieldsNonZero(int record, BigDecimal taxablePayToDate) {
        checkIrrelevantRTIFields(record, BigDecimal.valueOf(100), taxablePayToDate, BigDecimal.valueOf(100), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
    }

    @Then("^RTI record (\\d+) has all irrelevant fields equal to null$")
    public void checkRTIRecordIrrelevantFieldsNull(int record) {
        checkIrrelevantRTIFields(record, null, null, null, null, null, null, null);
    }

    @Then("^RTI record (\\d+) has all irrelevant fields equal to zero$")
    public void checkRTIRecordIrrelevantFieldsZero(int record) {
        checkIrrelevantRTIFields(record, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Then("^RTI record (\\d+) has an optional irrelevant RTI field missing and the other irrelevant RTI fields equal to null or zero$")
    public void checkRTIRecordIrrelevantFieldsMissingNullOrZero(int record) {
        checkIrrelevantRTIFields(record, null, null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null);
    }

    @Then("^RTI record (\\d+) has an optional irrelevant RTI field missing and the other irrelevant RTI fields with values$")
    public void checkRTIRecordIrrelevantFieldsMissingOrValue(int record) {
        checkIrrelevantRTIFields(record, BigDecimal.valueOf(100), BigDecimal.valueOf(300), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, null, BigDecimal.ONE);
    }


    @Then("^irrelevant record observation for unique payment id (\\d+) exists$")
    public void checkIrrelevantRecordObservationExists(String uniquePaymentId) {
        checkObservationCodeAndDescriptionExists(apiCall, IRRELEVANT_RECORD_CODE, "Payment " + uniquePaymentId + IRRELEVANT_RECORD_REGEX, uniquePaymentId);
    }

    @SuppressWarnings("squid:S00107")
    private void checkIrrelevantRTIFields(int record,
                                          BigDecimal taxablePay,
                                          BigDecimal taxablePayToDate,
                                          BigDecimal totalTaxToDate,
                                          BigDecimal employeesContributionsToDate,
                                          BigDecimal pensionContributionsYearToDate,
                                          BigDecimal pensionContributionsNotPaidYeartoDate,
                                          BigDecimal benefitsTaxedYeartoDate) {
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(record) + TAXABLE_PAY, equalToValue(taxablePay))
                .body(getRTIRecordJsonPath(record) + TAXABLE_PAY_TO_DATE, equalToValue(taxablePayToDate))
                .body(getRTIRecordJsonPath(record) + TOTAL_TAX_TO_DATE, equalToValue(totalTaxToDate))
                .body(getRTIRecordJsonPath(record) + EMPLOYEES_NI_CONTRIBUTIONS_TO_DATE, equalToValue(employeesContributionsToDate))
                .body(getRTIRecordJsonPath(record) + PENSION_CONTRIBUTIONS_UNDER_NET_PAY_TO_DATE, equalToValue(pensionContributionsYearToDate))
                .body(getRTIRecordJsonPath(record) + PENSION_CONTRIBUTIONS_NOT_UNDER_NET_PAY_TO_DATE, equalToValue(pensionContributionsNotPaidYeartoDate))
                .body(getRTIRecordJsonPath(record) + VALUE_OF_BENEFITS_TO_DATE, equalToValue(benefitsTaxedYeartoDate));
    }

}
