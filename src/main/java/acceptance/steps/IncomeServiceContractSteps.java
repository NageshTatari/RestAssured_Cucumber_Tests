package acceptance.steps;


import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.hamcrest.Matchers.*;


@SuppressWarnings("all")
public class IncomeServiceContractSteps {

    private final APICall apiCall;

    public IncomeServiceContractSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }


    @Given("^income service bad path URL$")
    public void incomeServiceBadPathURL() {

        apiCall.setRequestParams("/gcii/income/QQ111301A?startdate=2015-04-01&enddate=2015-06-15&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Then("^a response is received with the status code \"([^\"]*)\"$")
    public void aResponseIsReceivedWithTheStatusAnd(String code) {

        int statusCode = Integer.parseInt(code);
        apiCall.getResponse().then().statusCode(statusCode);

    }


    @Then("^response body contains agreed headers with no data fields$")
    public void aResponseBodyContainsAgreedHeadersWithNoDataFields() {

        String responseBody = apiCall.getResponse().getBody().asString();

        apiCall.getResponse().then().body("$", hasKey("preparedIncomeStreams"));
        apiCall.getResponse().then().body("preparedIncomeStreams.any{it.containsKey('employerPayeRef')}", is(false));
        apiCall.getResponse().then().body("preparedIncomeStreams.any{it.containsKey('preparedIncome')}", is(false));


        apiCall.getResponse().then().body("$", hasKey("observations"));
        apiCall.getResponse().then().body("observations.any{it.containsKey('code')}", is(true));
        apiCall.getResponse().then().body("observations.any{it.containsKey('description')}", is(true));

        apiCall.getResponse().then().body("$", hasKey("confidence"));

        Assert.assertTrue("data field not found in response json body ", responseBody.contains("data"));
        Assert.assertTrue("identity field not found in response json body ", responseBody.contains("identity"));

        apiCall.getResponse().then().body("$", hasKey("rti"));
        apiCall.getResponse().then().body("rti.any{it.containsKey('taxYear')}", is(false));
        apiCall.getResponse().then().body("rti.any{it.containsKey('totalEmployerNIContributionsInPeriod')}", is(false));


    }


    @Then("^response body contains agreed headers with data fields$")
    public void aResponseBodyContainsAgreedHeadersWithDataFields() {

        String responseBody = apiCall.getResponse().getBody().asString();

        apiCall.getResponse().then().body("$", hasKey("preparedIncomeStreams"));

        apiCall.getResponse().then().body("preparedIncomeStreams.any{it.containsKey('employerPayeRef')}", is(true));
        apiCall.getResponse().then().body("preparedIncomeStreams.any{it.containsKey('hmrcOfficeNumber')}", is(true));
        apiCall.getResponse().then().body("preparedIncomeStreams.any{it.containsKey('payrollId')}", is(true));
        apiCall.getResponse().then().body("preparedIncomeStreams.any{it.containsKey('preparedIncome')}", is(true));

        apiCall.getResponse().then().body("preparedIncomeStreams.preparedIncome[0].any{it.containsKey('paymentDate')}", is(true));
        apiCall.getResponse().then().body("preparedIncomeStreams.preparedIncome[0].any{it.containsKey('uniquePaymentId')}", is(true));
        apiCall.getResponse().then().body("preparedIncomeStreams.preparedIncome[0].any{it.containsKey('taxablePayToDate')}", is(true));
        apiCall.getResponse().then().body("preparedIncomeStreams.preparedIncome[0].any{it.containsKey('taxablePayInPeriod')}", is(true));
        apiCall.getResponse().then().body("preparedIncomeStreams.preparedIncome[0].any{it.containsKey('occupationalPension')}", is(true));
        apiCall.getResponse().then().body("preparedIncomeStreams.preparedIncome[0].any{it.containsKey('employerName1')}", is(true));

        apiCall.getResponse().then().body("$", hasKey("observations"));

        apiCall.getResponse().then().body("observations.any{it.containsKey('code')}", is(true));
        apiCall.getResponse().then().body("observations.any{it.containsKey('description')}", is(true));
        apiCall.getResponse().then().body("observations.any{it.containsKey('identifier')}", is(true));

        apiCall.getResponse().then().body("$", hasKey("confidence"));

        Assert.assertTrue("data filed not found in response json body ", responseBody.contains("data"));
        Assert.assertTrue("identity filed not found in response json body ", responseBody.contains("identity"));

        apiCall.getResponse().then().body("$", hasKey("rti"));

        apiCall.getResponse().then().body("rti.any{it.containsKey('taxYear')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('currentTaxYear')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('hmrcOfficeNumber')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('employerPayeRef')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('employerName1')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('nino')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('surname')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('gender')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('uniqueEmploymentSequenceNumber')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('payrollId')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('taxablePayInPeriod')}", is(true));


        apiCall.getResponse().then().body("rti.any{it.containsKey('employeePensionContributions')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('taxDeducted')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('taxablePayToDate')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('totalTaxToDate')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('employeesContributionsToDate')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('numberOfNormalHours')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('payFrequency')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('paymentDate')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('taxMonthNumber')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('numberOfEarningsPeriods')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('uniquePaymentId')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('paymentConfidenceStatus')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('occupationalPension')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('benefitsTaxedYeartoDate')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('pensionContributionsYearToDate')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('pensionContributionsNotPaidYeartoDate')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('taxCode')}", is(true));
        apiCall.getResponse().then().body("rti.any{it.containsKey('totalEmployerNIContributionsInPeriod')}", is(true));
    }


    @Then("^response header parameter Content-Length = number$")
    public void responseHeaderParameterContentLength() {

        String cLength = apiCall.getResponse().header("Content-Length");

        Assert.assertTrue(cLength, isNumeric(cLength));
    }


    @Then("^response header parameter Content-Type = \"([^\"]*)\"$")
    public void responseHeaderParameterContentType(String contentType) {

        String cType = apiCall.getResponse().header("Content-Type");
        Assert.assertTrue(cType.contains(contentType));
    }


    @Then("^response body contains error message (.*) and \"([^\"]*)\"$")
    public void responseBodyContainsErrorMessage(String message, String statusCode) {

        String responseBody = apiCall.getResponse().getBody().asString();

        Assert.assertTrue("Incorrect error message displayed ", responseBody.contains(message));
        Assert.assertTrue("Incorrect error status code", responseBody.contains(statusCode));

    }


    @Then("^response header parameter Date = Date$")
    public void responseHeaderParameterDate() {

        String rDate = apiCall.getResponse().header("Date");
        Assert.assertTrue("Date is empty", rDate != null);
    }


    @Given("^valid query parameters$")
    public void validQueryParameters() {

        apiCall.setRequestParams("QQ111001A?startdate=2015-04-01&enddate=2015-05-14&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


    @Given("^request header parameter Accept = \"([^\"]*)\"$")
    public void requestHeaderParameterAccept(String accept) {

        apiCall.setRequestHeaderAccept(accept);
    }


    @Given("^request header parameter Request Method = \"([^\"]*)\"$")
    public void requestHeaderParameterRequestMethod(String method) {

        apiCall.setRequestMethod(method);
    }


    @Given("^request header parameter Accept-Encoding = \"([^\"]*)\"$")
    public void requestHeaderParameterAcceptEncoding(String acceptEncoding) {

        apiCall.setRequestHeaderAcceptEncoding(acceptEncoding);
    }


    @Then("^response header parameter Cache-Control = \"([^\"]*)\"$")
    public void responseHeaderParameterCacheControl(String cacheControl) {

        String cControl = apiCall.getResponse().header("Cache-Control");
        Assert.assertEquals(cControl, cacheControl);
    }


    @Then("^response header parameter Content-Encoding = \"([^\"]*)\"$")
    public void responseHeaderParameterContentEncoding(String contentEncoding) {

        String cEncoding = apiCall.getResponse().header("Content-Encoding");
        Assert.assertEquals(cEncoding, contentEncoding);
    }


    @Given("^valid request header parameters$")
    public void validRequestHeaderParameters() {

        apiCall.setRequestHeaderAccept("application/json");
        apiCall.setRequestHeaderAcceptEncoding("gzip, deflate");
    }


    @Given("^query parameter start date is in the format ([^\"]*)$")
    public void queryParameterStartDateIsInTheFormat(String startDate) {

        apiCall.setRequestStartDate(startDate);
    }


    @Given("^query parameter end date is in the format ([^\"]*)$")
    public void queryParameterEndDateIsInTheFormat(String endDate) {

        apiCall.setRequestEndDate(endDate);
    }


    @Given("^start date is prior to or equal to end date$")
    public void startDateIsPriorToOrEqualToEndDate() {

        String sDate = apiCall.getRequestStartDate();
        String eDate = apiCall.getRequestEndDate();

        LocalDate startDate = LocalDate.parse(sDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(eDate, DateTimeFormatter.ISO_LOCAL_DATE);

        if (!(startDate.isBefore(endDate) || startDate.equals(endDate))) {
            Assert.assertFalse("start date is after end date"
                    , startDate.isAfter(endDate));
        }
    }


    @Given("^RTI service is manually stopped$")
    public void rtiServiceIsManuallyStopped() {

        //Take RTI service manually down
    }


    @Given("^NINO format = \"([^\"]*)\"$")
    public void ninoFormat(String nino) {

        apiCall.setRequestNino(nino);
    }


    @Given("^first name = ([^\"]*)$")
    public void firstName(String forename) {

        apiCall.setRequestForename(forename);
    }


    @Given("^middle name = ([^\"]*)$")
    public void middleName(String secondForename) {

        apiCall.setRequestSecondForename(secondForename);
    }


    @Given("^last name = ([^\"]*)$")
    public void lastName(String surname) {

        apiCall.setRequestSurname(surname);
    }

    @Given("^initials = ([^\"]*)$")
    public void initials(String initials) {

        apiCall.setRequestInitials(initials);

    }


    @Given("^sex = ([^\"]*)$")
    public void sex(String sex) {

        apiCall.setRequestSex(sex);

    }

    @Given("^date of birth = ([^\"]*)$")
    public void dateOfBirth(String dateOfBirth) {

        apiCall.setRequestDob(dateOfBirth);

    }

    @Then("^response header parameter Server = \"([^\"]*)\"$")
    public void responseHeaderParameterServer(String serverName) {

        String server = apiCall.getResponse().header("Server");
        Assert.assertEquals(server, serverName);
    }


    @Then("^response header parameter Vary = \"([^\"]*)\"$")
    public void responseHeaderParameterVary(String vary) {

        String hVary = apiCall.getResponse().header("Vary");
        Assert.assertEquals(vary, hVary);
    }


    @Then("^response header parameter Allow = \"([^\"]*)\"$")
    public void responseHeaderParameterAllow(String allow) {

        String hAllow = apiCall.getResponse().header("Allow");
        Assert.assertEquals(allow, hAllow);
    }


    @Given("^NINO format incorrect \"([^\"]*)\"$")
    public void ninoFormatIncorrect(String nino) {

        apiCall.setRequestNino(nino);
    }


    @Given("^Invalid nino \"([^\"]*)\"$")
    public void invalidNino(String nino) {

        apiCall.setRequestNino(nino);
    }

    @Given("^start date format incorrect ([^\"]*)$")
    public void startDateFormatIncorrect(String startDate) {

        apiCall.setRequestStartDate(startDate);
    }


    @Given("^end date format incorrect ([^\"]*)$")
    public void endDateFormatIncorrect(String endDate) {

        apiCall.setRequestEndDate(endDate);
    }


    @Given("^start date not supplied$")
    public void startDateNotSupplied() {

        apiCall.setRequestStartDate("");
    }


    @Given("^end date not supplied$")
    public void endDateNotSupplied() {

        apiCall.setRequestEndDate("");
    }


    @Given("^Start date is equal to end date$")
    public void startDateIsEqualToEndDate() {

        String sDate = apiCall.getRequestStartDate();
        String eDate = apiCall.getRequestEndDate();

        LocalDate startDate = LocalDate.parse(sDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(eDate, DateTimeFormatter.ISO_LOCAL_DATE);

        if (!(startDate.equals(endDate))) {
            Assert.assertFalse("start and end dates are not equal"
                    , startDate.isAfter(endDate));
        }
    }


    @Given("^Start date > end date$")
    public void startDateEndDate() {

        String sDate = apiCall.getRequestStartDate();
        String eDate = apiCall.getRequestEndDate();

        LocalDate startDate = LocalDate.parse(sDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(eDate, DateTimeFormatter.ISO_LOCAL_DATE);

        if (!(startDate.isAfter(endDate))) {
            Assert.assertFalse("start date is before end date"
                    , startDate.isBefore(endDate));
        }
    }


}
