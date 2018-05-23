package acceptance.steps;

import acceptance.helpers.APICall;
import cucumber.api.java.en.Given;

public class IncomeServiceMultipleIncomeStreamRecordsSteps {

    private final APICall apiCall;

    @SuppressWarnings("squid:S1192")
    public IncomeServiceMultipleIncomeStreamRecordsSteps(APICall apiCall) {
        this.apiCall = apiCall;
    }

    @Given("^there is a citizen with three RTI records for a time period where two have a common employer paye reference, hmrc office number, payroll id, nps number and third RTI record is distinct$")
    public void setupRequest1() {
        apiCall.setRequestParams("QQ332001A?startdate=2015-05-31&enddate=2015-07-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with three RTI records for a time period, both with a common employer paye reference, hmrc office number, payroll id, nps number and second RTI record has occupational pension flag set to true$")
    public void setupRequest2() {
        apiCall.setRequestParams("QQ332001A?startdate=2015-08-31&enddate=2015-10-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with three RTI records for a time period, two with a common employer paye reference, hmrc office number and different payroll id and nps number and second RTI record has occupational pension flag set to true$")
    public void setupRequest3() {
        apiCall.setRequestParams("QQ332001A?startdate=2014-08-31&enddate=2014-10-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

    @Given("^there is a citizen with five RTI records for a time period$")
    public void setupRequest4() {
        apiCall.setRequestParams("QQ332001A?startdate=2015-11-30&enddate=2016-03-31&forename=JOHN&secondforename=BOB&surname=DIXON&initials=JB&dob=1990-06-01");
    }

}
