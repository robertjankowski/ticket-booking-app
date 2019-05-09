package com.jankowski.ticketapp.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtils {

    public static final long MIN_MINUTES_TO_BUY = 15;

    public static boolean compareDates(LocalDateTime before, LocalDateTime after, long minMinutesToBuy) {
        var minutesDuration = ChronoUnit.MINUTES.between(before, after);
        if (minutesDuration > minMinutesToBuy || minutesDuration < 0)
            return false;
        return true;
    }
}
