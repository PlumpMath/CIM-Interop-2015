package org.endeavourhealth.cim.common.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.endeavourhealth.cim.common.serializer.PartialDateTimeDeserializer;
import org.endeavourhealth.cim.common.serializer.PartialDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonDeserialize(using = PartialDateTimeDeserializer.class)
@JsonSerialize(using = PartialDateTimeSerializer.class)
public class PartialDateTime {
    private final static String FMT_UNKNOWN = "unknown";
    private final static String FMT_PRECISION_YEAR = "%04d";
    private final static String FMT_PRECISION_MONTH = "%04d-%02d";
    private final static String FMT_PRECISION_DAY = "%04d-%02d-%02d";
    private final static String FMT_PRECISION_MINUTE = "%04d-%02d-%02dT%02d:%02d";
    private final static String FMT_PRECISION_SECOND = "yyyy-MM-dd'T'HH:mm:ss";

    private final String dateTimeString;
    private final LocalDateTime dateTime;

    public PartialDateTime() {
        this(FMT_UNKNOWN);
    }

    public PartialDateTime(String value) {
        this.dateTimeString = value;
        this.dateTime = parse(value);
    }

    public PartialDateTime(LocalDateTime dt) {
        this(dt.format(DateTimeFormatter.ofPattern(FMT_PRECISION_SECOND)));
    }

    public PartialDateTime(int year, int month, int day, int hour, int minute, int second) {
        this(LocalDateTime.of(year, month, day, hour, minute, second));
    }

    public PartialDateTime(int year, int month, int day, int hour, int minute) {
        this(String.format(FMT_PRECISION_MINUTE, year, month, day, hour, minute));
    }

    public PartialDateTime(int year, int month, int day) {
        this(String.format(FMT_PRECISION_DAY, year, month, day));
    }

    public PartialDateTime(int year, int month) {
        this(String.format(FMT_PRECISION_MONTH, year, month));
    }

    public PartialDateTime(int year) {
        this(String.format(FMT_PRECISION_YEAR, year));
    }

    public String asString() {
        return dateTimeString;
    }

    public LocalDateTime asDateTime() {
        return dateTime;
    }

    public boolean isUnknown() {
        return dateTimeString.equals(FMT_UNKNOWN);
    }

    private LocalDateTime parse(String dateString) {
        if (dateString.equals(FMT_UNKNOWN)) {
            return null;
        }
        switch(dateString.length())
        {
            case 4:
                return LocalDateTime.parse(dateString + "-01-01T00:00:00", DateTimeFormatter.ofPattern(FMT_PRECISION_SECOND));
            case 7:
                return LocalDateTime.parse(dateString + "-01T00:00:00", DateTimeFormatter.ofPattern(FMT_PRECISION_SECOND));
            case 10:
                return LocalDateTime.parse(dateString + "T00:00:00", DateTimeFormatter.ofPattern(FMT_PRECISION_SECOND));
            case 16:
                return LocalDateTime.parse(dateString + ":00", DateTimeFormatter.ofPattern(FMT_PRECISION_SECOND));
            case 19:
                return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(FMT_PRECISION_SECOND));
            default:
                throw new UnsupportedOperationException("Invalid DateTime string format (invalid length):" + dateString);
        }
    }

    public static PartialDateTime now() {
        return new PartialDateTime(LocalDateTime.now());
    }

    public static PartialDateTime nowWithMinutePrecision() {
        LocalDateTime now = LocalDateTime.now();
        return new PartialDateTime(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute());
    }

    public static PartialDateTime date() {
        LocalDateTime now = LocalDateTime.now();
        return new PartialDateTime(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    public static PartialDateTime unknown() {
        return new PartialDateTime();
    }
}