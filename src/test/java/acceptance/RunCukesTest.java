package acceptance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/acceptance"},
        format = {"json:target/cucumber.reports/cucumber.json", "html:target/cucumber.reports"},
       tags = {"@IncomeServiceDuplicates," +
               "@RepeatRecords," +
               "@IrrelevantForIncome," +
               "@OccupationalPension," +
               "@SingleIncomeStreamRecords," +
               "@MultipleIncomeStreamsRecords," +
               "@SupersededRecords," +
               "@OrderByPaymentDateAndUniquePaymentId," +
               "@SequentialRecords," +
               "@YearToDateReset," +
               "@IncomeService," +
               "@Pipeline," +
               "@IncomeServiceContractTests"
       }
)
public class RunCukesTest {

}
