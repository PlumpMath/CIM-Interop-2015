package org.endeavourhealth.common.core;

import org.hl7.fhir.instance.model.DateTimeType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static final SimpleDateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public static final Date DOTNET_MINIMUM_DATE = new GregorianCalendar(1, 0, 1, 0, 0, 0).getTime();
    public static final Date DOTNET_MAXIMUM_DATE = new GregorianCalendar(9999, 11, 31, 23, 59, 59).getTime();

    public static String formatDateAsISO8601(Date date) {
        return ISO8601_DATE_FORMAT.format(date);
    }

    public static Date Parse(String dateTime) {
        return new DateTimeType(dateTime).getValue();
    }
}
