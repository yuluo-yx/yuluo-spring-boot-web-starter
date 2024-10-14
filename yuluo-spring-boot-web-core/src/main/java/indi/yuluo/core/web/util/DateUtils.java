package indi.yuluo.core.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Optional;

public final class DateUtils {

    private DateUtils() {
    }

    private final static Logger log = LoggerFactory.getLogger(DateUtils.class);

    public static String getCurrentTime() {

        return String.valueOf(LocalDateTime.now());
    }

    public static long getDate() {

        return new Date().getTime();
    }

    private static final String[] DATE_FORMATS = {
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd HH:mm:ss"
    };

    /**
     * convert date to timestamp
     * @param date date
     * @return timestamp
     */
    public static Optional<Long> getTimeStampFromSomeFormats(String date) {
        for (String dateFormat : DATE_FORMATS) {
            try {
                DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                        .appendPattern(dateFormat)
                        // enable string conversion in strict mode.
                        .parseStrict()
                        .toFormatter();
                LocalDateTime time = LocalDateTime.parse(date, dateTimeFormatter);
                return Optional.of(time.toInstant(ZoneOffset.UTC).toEpochMilli());
            } catch (Exception e) {
                log.warn("Error parsing date '{}' with format '{}': {}",
                        date, dateFormat, e.getMessage());
            }
        }

        log.error("Error parsing date '{}', no corresponding date format", date);
        return Optional.empty();
    }

    /**
     * convert format data to timestamp
     */
    public static Optional<Long> getTimeStampFromFormat(String date, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            return Optional.of(dateTime.toInstant(ZoneOffset.UTC).toEpochMilli());
        } catch (Exception e) {
            log.error("Error parsing date '{}' with format '{}': {}",
                    date, format, e.getMessage());
        }

        return Optional.empty();
    }

    /**
     * 2024-09-10T16:34:18.839424900 -> 2024年9月10号16时34分
     */
    public static String formatDateTime(String input) {

        LocalDateTime dateTime = LocalDateTime.parse(input.replace("Z", ""), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy年M月d号HH时mm分");

        return dateTime.format(outputFormatter);
    }

}
