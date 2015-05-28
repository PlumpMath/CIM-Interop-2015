package org.endeavourhealth.cim.common;

import java.util.UUID;

public interface IDataAdapter {
    String getPatient(UUID patientId);
}