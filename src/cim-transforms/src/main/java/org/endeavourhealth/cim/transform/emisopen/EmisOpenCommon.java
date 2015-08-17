package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomorganisationinformation.OrganisationInformation;
import org.hl7.fhir.instance.model.Reference;
import org.hl7.fhir.instance.model.Resource;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmisOpenCommon {

    //public final static String EMISOPEN_NAMESPACE = "http://www.e-mis.com/emisopen/MedicalRecord";
    public final static SimpleDateFormat EMISOPEN_DATEFORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public final static SimpleDateFormat EMISOPEN_TIMEFORMAT = new SimpleDateFormat("HH:mm");
    public final static String SCHEDULEADDITIONALACTOR_EXTENSION_URL = "http://www.e-mis.com/emisopen/extension/Schedule/AdditionalActor";

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

    public static Map<String, String> buildUserIdGuidMap(OrganisationInformation organisationInformation) {
        HashMap<String, String> userIdGuidMap = new HashMap<>();

        organisationInformation
                .getUserList()
                .getUser()
                .forEach(t ->
                        userIdGuidMap.put(t.getDBID().toString(), t.getGUID()));

        return userIdGuidMap;
    }

    public static Map<String, String> buildLocationIdGuidMap(OrganisationInformation organisationInformation) {
        HashMap<String, String> locationIdGuidMap = new HashMap<>();

        organisationInformation
                .getLocationTypeList()
                .getLocationType()
                .forEach(t ->
                        locationIdGuidMap.put(t.getDBID().toString(), t.getGUID()));

        return locationIdGuidMap;
    }
}
