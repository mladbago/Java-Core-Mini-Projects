package uj.wmii.pwj.delegations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Calc {

    BigDecimal calculate(String name, String start, String end, BigDecimal dailyRate) throws IllegalArgumentException {

        DateTimeFormatter delegateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm VV");

        ZonedDateTime beginningTime = ZonedDateTime.parse(start, delegateFormatter);
        ZonedDateTime endingTime = ZonedDateTime.parse(end, delegateFormatter);

        long minutesDelegated = Duration.between(beginningTime,endingTime).toMinutes();
        long hoursAfterGivenDay = (minutesDelegated / 60) % 24;

        BigDecimal fullDaysDelegated = new BigDecimal(minutesDelegated / (24 * 60));
        BigDecimal moneyToBeGiven = dailyRate.multiply(fullDaysDelegated);
        BigDecimal minutesNotGivenMoneyFor = findNotPayedDelegation(hoursAfterGivenDay, minutesDelegated, dailyRate);

        return moneyToBeGiven.add(minutesNotGivenMoneyFor).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal findNotPayedDelegation(long hoursAfterGivenDay, long minutesDelegated, BigDecimal dailyRate) {
        //sss
        if(hoursAfterGivenDay > 12)
            return dailyRate;
        else if (hoursAfterGivenDay > 8)
            return dailyRate.multiply(BigDecimal.valueOf(1.0 / 2.0));
        else if  (minutesDelegated % (24 * 60) > 0)
            return dailyRate.multiply(BigDecimal.valueOf(1.0 / 3.0));
        else return BigDecimal.valueOf(0.0);
    }
}
