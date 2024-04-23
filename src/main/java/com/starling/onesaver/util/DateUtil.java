package com.starling.onesaver.util;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    public static String convert(LocalDate date) {
        return date.atStartOfDay().atOffset(ZoneOffset.UTC).format(dateTimeFormatter);
    }
}
