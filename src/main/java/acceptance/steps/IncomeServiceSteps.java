package acceptance.steps;

import acceptance.helpers.APICall;

import static org.hamcrest.Matchers.*;

/**
 * Base class for all steps classes
 */
@SuppressWarnings("all")
class IncomeServiceSteps {

    private static final String PREPARED_INCOME_STREAMS = "preparedIncomeStreams";
    private static final String RTI = "rti";
    private static final String OBSERVATIONS = "observations";

    private static final String INCOME_ITEMS = ".preparedIncome";

    static final String UNIQUE_PAYMENT_ID = ".uniquePaymentId";
    static final String START_DATE = "?startdate=";
    static final String FORENAME = "&forename=";
    static final String SECOND_FORENAME = "&secondforename=";
    static final String SURNAME = "&surname=";
    static final String INITIALS = "&initials=";
    static final String DOB = "&dob=";
    static final String SEX = "&sex=";
    static final String END_DATE = "&enddate=";
    static final String PAYMENT_DATE = ".paymentDate";
    static final String TAXABLE_PAY = ".taxablePayInPeriod";
    static final String TAXABLE_PAY_TO_DATE = ".taxablePayToDate";
    static final String EMPLOYER_PAYE_REFERENCE = ".employerPayeRef";
    static final String HMRC_OFFICE_NUMBER = ".hmrcOfficeNumber";
    static final String PAYROLL_ID = ".payrollId";
    static final String NPS_NUMBER = ".uniqueEmploymentSequenceNumber";
    static final String OCCUPATIONAL_PENSION = ".occupationalPension";
    static final String DEATH_BENEFIT = ".pensionsDeathBenefit";
    static final String FLEXIBLY_ACCESS_PENSION_RIGHTS = ".flexiblyAccessingPensionRights";
    static final String EMPLOYER_NAME1 = ".employerName1";
    static final String TOTAL_TAX_TO_DATE = ".totalTaxToDate";
    static final String EMPLOYEES_NI_CONTRIBUTIONS_TO_DATE = ".employeesContributionsToDate";
    static final String PENSION_CONTRIBUTIONS_UNDER_NET_PAY_TO_DATE = ".pensionContributionsYearToDate";
    static final String PENSION_CONTRIBUTIONS_NOT_UNDER_NET_PAY_TO_DATE = ".pensionContributionsNotPaidYeartoDate";
    static final String VALUE_OF_BENEFITS_TO_DATE = ".benefitsTaxedYeartoDate";
    static final String PENSION_CONTRIBUTIONS_UNDER_NET_PAY = ".employeePensionContributions";
    static final String TOTAL_EMPLOYER_NI_CONTRIBUTIONS_IN_PERIOD = ".totalEmployerNIContributionsInPeriod";

    String getIncomeStreamsJsonPath() {
        return PREPARED_INCOME_STREAMS;
    }

    String getIncomeStreamJsonPath(int stream) {
        return String.format("%s[%d]", PREPARED_INCOME_STREAMS, stream);
    }

    String getIncomeItemsJsonPath(int stream) {
        return String.format("%s[%d]%s", PREPARED_INCOME_STREAMS, stream, INCOME_ITEMS);
    }

    String getIncomeItemJsonPath(int stream, int item) {
        return String.format("%s[%d]%s[%d]", PREPARED_INCOME_STREAMS, stream, INCOME_ITEMS, item);
    }

    String getRTIJsonPath() {
        return RTI;
    }

    String getRTIRecordJsonPath(int record) {
        return String.format("%s[%d]", RTI, record);
    }

    String getObservationsJsonPath() {
        return OBSERVATIONS;
    }

    void checkObservationCodeAndDescriptionExists(APICall apiCall, String code, String description, String identifier) {
        apiCall.getResponse().then()
                .body(getObservationsJsonPath() + ".code", hasItem(code))
                .body(getObservationsJsonPath() + ".identifier", hasItem(identifier))
                .body(getObservationsJsonPath() + ".description", hasItem(description));
    }

    void checkRTIRecordUniquePaymentId(APICall apiCall, int record, int uniquePaymentId) {
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(record) + UNIQUE_PAYMENT_ID, equalTo(uniquePaymentId));
    }

    int getPreparedRecordUniquePaymentId(APICall apiCall, int incomeStream, int record) {
        return apiCall.getResponse().path(getIncomeItemJsonPath(incomeStream, record) + UNIQUE_PAYMENT_ID);
    }

    int getRTIRecordUniquePaymentId(APICall apiCall, int record) {
        return apiCall.getResponse().path(getRTIRecordJsonPath(record) + UNIQUE_PAYMENT_ID);
    }

    void checkRTIRecordTaxablePay(APICall apiCall, int record, int taxablePay) {
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(record) + TAXABLE_PAY, equalTo((float) taxablePay));
    }

    void checkRTIRecordTaxablePayToDate(APICall apiCall, int record, int taxablePayToDate) {
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(record) + TAXABLE_PAY_TO_DATE, equalTo((float) taxablePayToDate));
    }

}
