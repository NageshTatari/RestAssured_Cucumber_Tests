package acceptance.steps;

import acceptance.helpers.APICall;
import com.jayway.restassured.http.ContentType;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;

public class IncomeServiceAPISteps extends IncomeServiceSteps {

    private final APICall apiCall;

    public IncomeServiceAPISteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Then("^the response data is provided in json format$")
    public void dataIsProvidedInJsonFormat() {
        apiCall.getResponse().then().contentType(ContentType.JSON);
    }


    @Then("^the response have agreed data fields are provided$")
    public void agreedDataFieldsAreProvided() {
        apiCall.getResponse().then().body(getIncomeStreamsJsonPath(), hasSize(1));
        apiCall.getResponse().then().body(getObservationsJsonPath(), hasSize(1));
        apiCall.getResponse().then().body(getRTIJsonPath(), hasSize(1));
    }


    @Then("^the fields in the income streams match the equivalent fields in RTI$")
    public void agreedIncomeStreamDataFieldsMatchRTI() {
        // Exclude occupational pension from this - special handling elsewhere

        Object obj = apiCall.getResponse().path(getRTIRecordJsonPath(0) + EMPLOYER_PAYE_REFERENCE);
        apiCall.getResponse().then()
                .body(getIncomeStreamJsonPath(0) + EMPLOYER_PAYE_REFERENCE, equalTo(obj));

        obj = apiCall.getResponse().path(getRTIRecordJsonPath(0) + HMRC_OFFICE_NUMBER);
        apiCall.getResponse().then()
                .body(getIncomeStreamJsonPath(0) + HMRC_OFFICE_NUMBER, equalTo(obj));

        obj = apiCall.getResponse().path(getRTIRecordJsonPath(0) + PAYROLL_ID);
        apiCall.getResponse().then()
                .body(getIncomeStreamJsonPath(0) + PAYROLL_ID, equalTo(obj));

        obj = apiCall.getResponse().path(getRTIRecordJsonPath(0) + NPS_NUMBER);
        apiCall.getResponse().then()
                .body(getIncomeStreamJsonPath(0) + NPS_NUMBER, equalTo(obj));

        obj = apiCall.getResponse().path(getRTIRecordJsonPath(0) + UNIQUE_PAYMENT_ID);
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(0) + UNIQUE_PAYMENT_ID, equalTo(obj));

        obj = apiCall.getResponse().path(getRTIRecordJsonPath(0) + PAYMENT_DATE);
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(0) + PAYMENT_DATE, equalTo(obj));


        obj = apiCall.getResponse().path(getRTIRecordJsonPath(0) + TAXABLE_PAY_TO_DATE);
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(0) + TAXABLE_PAY_TO_DATE, equalTo(obj));


        obj = apiCall.getResponse().path(getRTIRecordJsonPath(0) + TAXABLE_PAY);
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(0) + TAXABLE_PAY, equalTo(obj));


        obj = apiCall.getResponse().path(getRTIRecordJsonPath(0) + EMPLOYER_NAME1);
        apiCall.getResponse().then()
                .body(getRTIRecordJsonPath(0) + EMPLOYER_NAME1, equalTo(obj));

    }


    @Then("^the response has (\\d+) prepared income records in income stream (\\d+) between ([^\"]*) and ([^\"]*) dates$")
    public void recordsAreReturnedBetweenDates(int recordsCount, int stream, String startDate, String endDate) {

        String sDate = "";
        String eDate = "";

        apiCall.getResponse().then().body(getIncomeItemsJsonPath(stream), hasSize(recordsCount));

        LocalDate dateStart = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate dateEnd = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);


        // getting first and last records dates for income service response
        final String preparedPaymentDate = getIncomeItemJsonPath(stream, 0) + PAYMENT_DATE;

        if (recordsCount == 1) {
            sDate = apiCall.getResponse().path(preparedPaymentDate);
            eDate = apiCall.getResponse().path(preparedPaymentDate);
        }

        if (recordsCount > 1) {
            int lastRecord = recordsCount - 1;
            sDate = apiCall.getResponse().path(preparedPaymentDate);
            eDate = apiCall.getResponse().path(getIncomeItemJsonPath(stream, lastRecord) + PAYMENT_DATE);
        }


        //converting income service response start and end date string to actual dates
        LocalDate firstRecordPaymentDate = LocalDate.parse(sDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate lastRecordPaymentDate = LocalDate.parse(eDate, DateTimeFormatter.ISO_LOCAL_DATE);


        //verifying the income service response records are in between start and end dates
        if (!(dateStart.isBefore(firstRecordPaymentDate) || dateStart.equals(firstRecordPaymentDate))) {
            Assert.assertFalse("first record payment date earlier then specified start date"
                    , dateStart.isAfter(firstRecordPaymentDate));
        }

        if (!(dateEnd.isAfter(lastRecordPaymentDate) || dateEnd.equals(lastRecordPaymentDate))) {
            Assert.assertFalse("Last record payment date later then specified end date"
                    , dateEnd.isBefore(lastRecordPaymentDate));
        }

    }

}
