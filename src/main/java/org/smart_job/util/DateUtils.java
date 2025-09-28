package org.smart_job.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DB_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Format to show on UI
    public static String formatForDisplay(LocalDate date) {
        if (date == null) return "";

        return date.format(DISPLAY_FORMATTER);
    }

    public static String formatForDB(LocalDate date, String pattern) {
        if (date == null) return "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    // Parse string from DB -> LocalDate
    public static LocalDate parseFromDb(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        return LocalDate.parse(dateStr, DB_FORMATTER);
    }

    // Optional: format to store in DB
    public static String formatForDb(LocalDate date) {
        if (date == null) return null;
        return date.format(DB_FORMATTER);
    }
}
