package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;


public class IncomeServicePipelineSteps {

    private final APICall apiCall;

    public IncomeServicePipelineSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^There is a citizen with twelve RTI records for a time period for single employment$")
    public void setUpRequest1() {
        apiCall.setRequestParams("QQ111004A?startdate=2014-08-30&enddate=2015-07-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^There is a citizen with thirteen RTI records for a time period for multiple employments$")
    public void setUpRequest2() {
        apiCall.setRequestParams("QQ111005A?startdate=2014-08-30&enddate=2015-07-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }


}
