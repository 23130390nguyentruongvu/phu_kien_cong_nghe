package vn.edu.hcmuaf.fit.pkcn.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class DateFormatUtils {
    public final static String PATTERN_DATETIME = "dd/MM/yyyy HH:mm";
    public final static String PATTERN_DATE = "dd/MM/yyyy";

    public static String formatDate(String pattern, LocalDateTime date) {
        if(date == null) return "NULL";
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
}
