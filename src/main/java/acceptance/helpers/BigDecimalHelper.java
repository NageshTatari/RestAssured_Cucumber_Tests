package acceptance.helpers;

import org.hamcrest.Matcher;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.Matchers.equalTo;

public class BigDecimalHelper {

    private BigDecimalHelper() {
    }

    public static Matcher<String> equalToValue(BigDecimal value) {
        return equalTo(value != null ? value.setScale(2, RoundingMode.UNNECESSARY).toString() : null);
    }

}
