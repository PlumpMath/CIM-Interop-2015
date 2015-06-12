package org.endeavourhealth.cim.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public interface IDataAdapter {
    String getTransformerTypeName();
    String getPatientRecordByPatientId(String odsCode, UUID patientId);
    String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber);
    String tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode);
    String tracePatientByNhsNumber(String nhsNumber);
    String createCondition(String odsCode, String request);
    ArrayList<UUID> getChangedPatients(String odsCode, Date date);
}