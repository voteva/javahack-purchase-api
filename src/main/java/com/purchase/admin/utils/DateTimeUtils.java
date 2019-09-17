package com.purchase.admin.utils;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeUtils {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withLocale(Locale.UK)
                    .withZone(ZoneId.systemDefault());

    public static String formatDateTime(@Nonnull Instant instant) {
        return FORMATTER.format(instant);
    }
}
