package org.endeavourhealth.cim.common;

import javax.xml.soap.SOAPException;
import java.util.UUID;

public interface IDataAdapter {
    String getPatientByPatientId(UUID patientId);
    String getPatientByNHSNumber(String nhsNumber);
    String createCondition(String request);
}