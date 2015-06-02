package org.endeavourhealth.cim.adapter;

import java.util.UUID;

public interface IDataAdapter {
    String getPatientByPatientId(UUID patientId);
    String getPatientByNHSNumber(String nhsNumber);
    String createCondition(String request);
}