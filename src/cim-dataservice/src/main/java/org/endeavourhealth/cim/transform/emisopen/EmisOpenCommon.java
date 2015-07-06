package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.hl7.fhir.instance.model.Reference;
import org.hl7.fhir.instance.model.Resource;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EmisOpenCommon {

    public final static String EMISOPEN_NAMESPACE = "http://www.e-mis.com/emisopen/MedicalRecord";
    public final static SimpleDateFormat EMISOPEN_DATEFORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public final static SimpleDateFormat EMISOPEN_TIMEFORMAT = new SimpleDateFormat("HH:mm");

    public static Date getDateAndTime(String dateString, String timeString) throws SerializationException {
        try {
            return EmisOpenCommon.EMISOPEN_DATEFORMAT.parse(dateString + " " + timeString);
        } catch (ParseException e) {
            throw new SerializationException("Could not parse date", e);
        }
    }

    public static Time getTime(String timeString) throws SerializationException {
        try {
            return new Time(EmisOpenCommon.EMISOPEN_TIMEFORMAT.parse(timeString).getTime());
        } catch (ParseException e) {
            throw new SerializationException("Could not parse time", e);
        }
    }

    public static Time addMinutesToTime(Date date, int minutes) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return new Time(cal.getTime().getTime());
    }
}
