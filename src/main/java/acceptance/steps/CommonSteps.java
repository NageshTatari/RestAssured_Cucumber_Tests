package acceptance.steps;

import acceptance.helpers.APICall;
import acceptance.helpers.StartUp;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpStatus;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class CommonSteps extends IncomeServiceSteps {

    private static final String GOOD_DATA_CODE = "0";
    private static final String GOOD_DATA_REGEX = "All data is good";

    private static final String END_POINT = "/gcii/income/";


    private static StartUp startUp = new StartUp();
    private static String baseURL = startUp.initialiseEnvironment();

    private final APICall apiCall;

    public CommonSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^There is a citizen with NINO ([^\"]*) and RTI data for the period between ([^\"]*) and ([^\"]*) dates$")
    public void setUpGenericRequest(String nino, String startDate, String endDate) {
        apiCall.setRequestParams(nino + START_DATE + startDate + END_DATE + endDate + FORENAME + "John"
                + SECOND_FORENAME + "Bob" + SURNAME + "Dixon" + INITIALS
                + "JB" + DOB + "1990-06-01" + SEX + "M");
    }

    @When("^I request income data for that citizen and time period$")
    public void makeThatRequest() {
        String url = baseURL + "/gcii/income/" + apiCall.getRequestParams();
        apiCall.setResponse(given().when().get(url));
        // Sanity check response was successful
        apiCall.getResponse().then().statusCode(HttpStatus.SC_OK);
    }


    @Then("^a successful response is received$")
    public void checkResponseIs200() {
        apiCall.getResponse().then().statusCode(HttpStatus.SC_OK);
    }

    @Then("^the response has (\\d+) observations$")
    public void checkObservationCount(int count) {
        apiCall.getResponse().then().body(getObservationsJsonPath(), hasSize(count));
    }

    @Then("^the response has (\\d+) RTI records$")
    public void checkResponseRTICount(int count) {
        apiCall.getResponse().then().body(getRTIJsonPath(), hasSize(count));
    }

    @Then("^the response has (\\d+) prepared income records in income stream (\\d+)$")
    public void checkPreparedIncomeRecordsCount(int count, int stream) {
        apiCall.getResponse().then().body(getIncomeItemsJsonPath(stream), hasSize(count));
    }

    @Then("^it is an all good data observation$")
    public void checkObservationIsGoodData() {
        checkObservationCodeAndDescriptionExists(apiCall, GOOD_DATA_CODE, GOOD_DATA_REGEX, null);
    }

    @Then("^the response has (\\d+) income streams$")
    public void checkTheResponseHasIncomeStreams(int count) {
        apiCall.getResponse().then().body(getIncomeStreamsJsonPath(), hasSize(count));
    }

    @Then("^income stream (\\d+) has (\\d+) income items$")
    public void checkItemCountForStream(int stream, int itemCount) {
        apiCall.getResponse().then().body(getIncomeItemsJsonPath(stream), hasSize(itemCount));
    }

    @Then("^income stream (\\d+) is ordered by payment date and unique payment id$")
    public void checkPreparedIncomeStreamIsOrderedByPaymentDateAndUniquePaymentId(int stream) {

        int itemCount = apiCall.getResponse().body().path(getIncomeItemsJsonPath(stream) + "[].size");
        for (int i = 1; i < itemCount; i++) {
            int currentUniquePaymentId = apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, i) + UNIQUE_PAYMENT_ID);
            int previousUniquePaymentId = apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, i - 1) + UNIQUE_PAYMENT_ID);
            LocalDate currentPaymentDate = LocalDate.parse(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, i) + PAYMENT_DATE), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate previousPaymentDate = LocalDate.parse(apiCall.getResponse().body().path(getIncomeItemJsonPath(stream, i - 1) + PAYMENT_DATE), DateTimeFormatter.ISO_LOCAL_DATE);
            Assert.assertTrue("Records are not ordered by payment date then unique payment id",
                    (previousPaymentDate.isBefore(currentPaymentDate) || previousPaymentDate.equals(currentPaymentDate))
                            && previousUniquePaymentId < currentUniquePaymentId);
        }
    }

    @Then("^RTI record (\\d+) has unique payment id (\\d+)$")
    public void checkRtiRecordHasAnEmployerWithUniquePaymentId(int record, int uniquePaymentId) {
        checkRTIRecordUniquePaymentId(apiCall, record, uniquePaymentId);
    }


    @When("^a call is made to the income service for that NINO and query parameters$")
    public void aCallIsMadeToTheIncomeServiceForThatNINOAndQ() {

        String url = baseURL + END_POINT + apiCall.getRequestNino() + START_DATE + apiCall.getRequestStartDate()
                + END_DATE + apiCall.getRequestEndDate() + FORENAME + apiCall.getRequestForename()
                + SECOND_FORENAME + apiCall.getRequestSecondForename() + SURNAME + apiCall.getRequestSurname()
                + INITIALS + apiCall.getRequestInitials() + DOB + apiCall.getRequestDob() + SEX + apiCall.getRequestSex();

        apiCall.setResponse(given().when().get(url));
    }


    @When("^a call is made to the income service$")
    public void aCallIsMadeToTheIncomeService() {

        String url = baseURL + END_POINT + apiCall.getRequestParams();
        apiCall.setResponse(given().when().get(url));
    }


    @When("^a call is made to the income service with incorrect request headers$")
    public void aCallIsMadeToTheIncomeServiceWithIncorrectRequestHeaders() {

        String url = baseURL + END_POINT + apiCall.getRequestParams();
        apiCall.setResponse(given().header("Accept", apiCall.getRequestHeaderAccept()).header("Accept-Encoding", apiCall.getRequestHeaderAcceptEncoding()).when().get(url));
    }


    @When("^a call is made to the income service with incorrect request method")
    public void aCallIsMadeToTheIncomeServiceWithIncorrectRequestMethod() {

        String url = baseURL + END_POINT + apiCall.getRequestParams();
        apiCall.setResponse(given().when().put(url));
    }


    @When("^a call is made to the income service with valid request headers$")
    public void aCallIsMadeToTheIncomeServiceWithValidRequestHeaders() {

        String url = baseURL + apiCall.getRequestParams();
        apiCall.setResponse(given().header("Accept", apiCall.getRequestHeaderAccept()).header("Accept-Encoding", apiCall.getRequestHeaderAcceptEncoding()).when().get(url));
    }

}
