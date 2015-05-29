package org.endeavourhealth.cim.adapter;

import org.endeavourhealth.cim.common.IDataAdapter;

import java.util.UUID;

public class PyrusiumDataAdapter implements IDataAdapter {
    public String getPatientByPatientId(UUID patientId) {
        // System-specific call to their provider api

        return "[..... PATIENT DATA PYRUS .....]";
    }

    public String getPatientByNHSNumber(String nhsNumber) {
        return null;
    }

    public String createObservation(String request) {
        return null;
    }
}
