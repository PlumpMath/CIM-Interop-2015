package org.endeavourhealth.cim.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public interface IDataAdapter {
    String getTransformerTypeName();
    String getPatientRecordByPatientId(UUID patientId);
    String getPatientDemographicsByNHSNumber(String nhsNumber);
    String getPatientDemographicsByQuery(String surname, Date dateOfBirth, String gender);
    String createCondition(String request);
    ArrayList<UUID> getChangedPatients(Date date);
}