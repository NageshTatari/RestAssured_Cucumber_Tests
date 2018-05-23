package acceptance.helpers;


public class StartUp {

    public String initialiseEnvironment() {
        return System.getProperty("baseIncomeURL", "http://localhost:8880");
    }

}
