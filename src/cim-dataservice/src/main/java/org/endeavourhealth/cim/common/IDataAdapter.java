package org.endeavourhealth.cim.common;

import java.util.UUID;

public interface IDataAdapter {
    String getPatientByPatientId(UUID patientId);
    String getPatientByNHSNumber(String nhsNumber);
    String createObservation(String request);
}