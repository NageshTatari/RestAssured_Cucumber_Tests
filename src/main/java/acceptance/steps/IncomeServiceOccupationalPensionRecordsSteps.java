package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import java.math.BigDecimal;

import static acceptance.helpers.BigDecimalHelper.equalToValue;
import static org.hamcrest.Matchers.equalTo;

public class IncomeServiceOccupationalPensionRecordsSteps extends IncomeServiceSteps {

    private static final String OCCUPATIONAL_PENSION_RECORD_CODE = "4";
    private static final String NOT_OCCUPATIONAL_PENSION_RECORD_CODE = "5";
    private static final String OCCUPATIONAL_PENSION_RECORD_REGEX = " marked as earnings but corrected to occupational pension";
    private static final String NO_OCCUPATIONAL_PENSION_RECORD_REGEX = " marked as occupational pension but corrected to earnings";

    private final APICall apiCall;

    public IncomeServiceOccupationalPensionRecordsSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^there is a citizen with one RTI record for a time period with death indicator benefit set to true$")
    public void setUpRequest1() {
        apiCall.setRequestParams("QQ111003A?startdate=2015-04-30&enddate=2015-05-01&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with one RTI record for a time period with flexibly accessing pension rights indicator set to true$")
    public void setUpRequest2() {
        apiCall.setRequestParams("QQ111003A?startdate=2015-05-31&enddate=2015-06-01&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with one RTI record for a time period with occupational pension indicator set to true and all relevant fields equal to zero or null$")
    public void setUpRequest3() {
        apiCall.setRequestParams("QQ111003A?startdate=2015-06-30&enddate=2015-07-01&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with one RTI record for a time period with occupational pension indicator set to true and all relevant fields equal to values$")
    public void setUpRequest4() {
        apiCall.setRequestParams("QQ111003A?startdate=2015-07-01&enddate=2015-08-01&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with one RTI record for a time period with all indicators set to false and all relevant fields equal to zero or null$")
    public void setUpRequest5() {
        apiCall.setRequestParams("QQ111003A?startdate=2015-08-31&enddate=2015-09-01&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with one RTI record for a time period with occupational pension indicator set to false and all relevant fields equal to values$")
    public void setUpRequest6() {
        apiCall.setRequestParams("QQ111003A?startdate=2015-09-30&enddate=2015-10-01&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Then("^RTI record (\\d+) has occupational pension flag set to true and all relevant fields equal to null and (\\d+)$")
    public void checkRTIRecordHasOccupationalPensionFlagSetToTrueAndAllRelevantFieldsEqualNullAndValue(int record, BigDecimal niContribution) {

        checkOccupationalPensionFields(record, true, null, niContribution);
    }


    @Then("^RTI record (\\d+) has occupational pension flag set to true and all relevant fields equal to (\\d+) and null$")
    public void chcekRTIRecordHasOccupationalPensionFlagSetToTrueAndAllRelevantFieldsEqualValueAndNull(int record, BigDecimal pensionContribution) {

        checkOccupationalPensionFields(record, true, pensionContribution, null);
    }

    @Then("^RTI record (\\d+) has occupational pension flag set to ([^\"]*) and all relevant fields equal to (\\d+) and (\\d+)$")
    public void checkRTIRecordHasOccupationalPensionFlagSetToTrueAndAllRelevantFieldsEqualValueOrNull(int record, Boolean flag, BigDecimal pensionContribution, BigDecimal niContribution) {

        checkOccupationalPensionFields(record, flag, pensionContribution, niContribution);
    }


    @Then("^RTI record (\\d+) has occupational pension flag set to ([^\"]*) and all relevant fields equal to zero and null$")
    public void checkRTIRecordHasOccupationalPensionFlagSetToTrueAndAllRelevantFieldsEqualToZeroOrNull(int record, Boolean flag) {

        checkOccupationalPensionFields(record, flag, BigDecimal.ZERO, null);
    }

    @Then("^RTI record (\\d+) has occupational pension flag set to true and all relevant fields equal to null and zero$")
    public void checkRTIRecordHasOccupationalPensionFlagSetToTrueAndAllRelevantFieldsEqualToNullOrZero(int record) {

        checkOccupationalPensionFields(record, true, BigDecimal.ZERO, BigDecimal.ZERO);

    }


    @Then("^([^\"]*) occupational pension record observation for unique payment id (\\d+) exists$")
    public void checkOccupationalPensionRecordObservationExists(String uniquePaymentId, String status) {
        if ("not an".equals(status)) {
            checkObservationCodeAndDescriptionExists(apiCall, NOT_OCCUPATIONAL_PENSION_RECORD_CODE, "Payment " + uniquePaymentId + NO_OCCUPATIONAL_PENSION_RECORD_REGEX, uniquePaymentId);
        }

        if ("an".equals(status)) {
            checkObservationCodeAndDescriptionExists(apiCall, OCCUPATIONAL_PENSION_RECORD_CODE, "Payment " + uniquePaymentId + OCCUPATIONAL_PENSION_RECORD_REGEX, uniquePaymentId);
        }
    }

    @Then("^RTI record (\\d+) has occupational pension flag set to false and all relevant fields equal to zero's$")
    public void checkRTIRecordHasOccupationalPensionFlagSetToFalseAndAllRelevantFieldsEqualToZeros(int record) {

        checkOccupationalPensionFields(record, false, BigDecimal.ZERO, BigDecimal.ZERO);
    }


    @Then("^RTI record (\\d+) has the death benefit indicator is set to ([^\"]*)$")
    public void checkRTIRecordHasDeathBenefitIndicator(int record, boolean flag) {

        checkDeathBenefitFlag(record, flag);
    }

    @Then("^RTI record (\\d+) has occupational pension indicator is set to ([^\"]*)$")
    public void checkRTIRecordHasOccupationalPensionIndicator(int record, boolean flag) {

        checkOccupationalPensionFlag(record, flag);
    }

    @Then("^RTI record (\\d+) has FlexiblyAccessingPensionRightsIndicator is set to ([^\"]*)$")
    public void checkRTIRecordHasFlexiblyAccessingPensionRightsIndicator(int record, boolean flag) {

        checkFlexiblyAccessPensionRights(record, flag);
    }

    @Then("^RTI record (\\d+) has occupational pension flag set to false and all relevant fields equal to null's$")
    public void checkRTIRecordHasOccupationalPensionFlagSetToFalseAndAllRelevantFieldsEqualToNulls(int record) {

        checkOccupationalPensionFields(record, false, null, null);
    }


    @Then("^RTI record (\\d+) non sequential record$")
    public void checkRTIRecordNonSequentialRecord(int record) {

        BigDecimal currentTaxablePay = new BigDecimal(apiCall.getResponse().body().path(getRTIRecordJsonPath(record) + TAXABLE_PAY).toString());
        BigDecimal currentTaxablePayToDate = new BigDecimal(apiCall.getResponse().body().path(getRTIRecordJsonPath(record) + TAXABLE_PAY_TO_DATE).toString());
        BigDecimal previousTaxablePayToDate = new BigDecimal(apiCall.getResponse().body().path(getRTIRecordJsonPath(record - 1) + TAXABLE_PAY_TO_DATE).toString());

        Assert.assertTrue("Records are not in sequence", previousTaxablePayToDate.add(currentTaxablePay).compareTo(currentTaxablePayToDate) != 0);
    }

    @Then("^income stream (\\d+) income item (\\d+) non sequential record$")
    public void checkPreparedIncomeStreamNonSequentialIncomeItem(int stream, int item) {

        BigDecimal currentTaxablePay = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item) + TAXABLE_PAY).toString());
        BigDecimal currentTaxablePayToDate = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item) + TAXABLE_PAY_TO_DATE).toString());
        BigDecimal previousTaxablePayToDate = new BigDecimal(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, item - 1) + TAXABLE_PAY_TO_DATE).toString());

        Assert.assertTrue("Income items are not in sequence", previousTaxablePayToDate.add(currentTaxablePay).compareTo(currentTaxablePayToDate) != 0);
    }

    @Then("^income stream (\\d+) income item (\\d+) has occupational pension flag set to ([^\"]*)$")
    public void checkPreparedRecordHasOccupationalPensionFlagSetToTrue(int stream, int item, Boolean flag) {

        checkOccupationalPensionFlag(stream, item, flag);
    }

    private void checkOccupationalPensionFields(int record,
                                                Boolean occupationalPension,
                                                BigDecimal employeePensionContribution,
                                                BigDecimal employerNiContribution) {
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(record) + OCCUPATIONAL_PENSION, equalTo(occupationalPension))
                .body(getRTIRecordJsonPath(record) + PENSION_CONTRIBUTIONS_UNDER_NET_PAY, equalToValue(employeePensionContribution))
                .body(getRTIRecordJsonPath(record) + TOTAL_EMPLOYER_NI_CONTRIBUTIONS_IN_PERIOD, equalToValue(employerNiContribution));
    }

    private void checkDeathBenefitFlag(int record,
                                       boolean flag) {
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(record) + DEATH_BENEFIT, equalTo(flag));
    }


    private void checkFlexiblyAccessPensionRights(int record,
                                                  boolean flag) {
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(record) + FLEXIBLY_ACCESS_PENSION_RIGHTS, equalTo(flag));
    }


    private void checkOccupationalPensionFlag(int record,
                                              boolean flag) {
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(record) + OCCUPATIONAL_PENSION, equalTo(flag));
    }


    private void checkOccupationalPensionFlag(int stream,
                                              int item,
                                              Boolean occupationalPension) {
        apiCall.getResponse().then()
                .body(getIncomeItemJsonPath(stream, item) + OCCUPATIONAL_PENSION, equalTo(occupationalPension));
    }

}
