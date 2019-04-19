package com.remote.doctor.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


public final class DateTimeUtils {
    private static final String FULL_DATE_PATTERN = "dd-MM-yyyy HH:mm";
    private static final String TIME_PATTERN = "HH:mm";

    public static String getCurrentTimeOrDate(OffsetDateTime sendDate) {
        OffsetDateTime now = OffsetDateTime.now();
        String stringSendDate;

        if (sendDate.getYear() == now.getYear() && sendDate.getDayOfYear() == now.getDayOfYear()) {
            stringSendDate = sendDate.format(DateTimeFormatter.ofPattern(TIME_PATTERN));
        } else {
            stringSendDate = sendDate.format(DateTimeFormatter.ofPattern(FULL_DATE_PATTERN));
        }

        return stringSendDate;
    }

    public static OffsetDateTime getStartDate(String startDate) {
        return OffsetDateTime.of(LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE), LocalTime.MIDNIGHT, ZoneOffset.UTC);
    }
}
