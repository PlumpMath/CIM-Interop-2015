package org.endeavourhealth.cim.dataManager;

import org.endeavourhealth.cim.transform.TransformException;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public interface IDataManager {
    String getPatientRecord(String odsCode, UUID patientId) throws Exception;
    String getPatientDemographics(String odsCode, UUID patientId) throws Exception;
    String getPatientDemographics(String odsCode, String nhsNumber) throws Exception;
    String tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception;
    String tracePatientByNhsNumber(String nhsNumber) throws Exception;
    String createCondition(String odsCode, String request) throws Exception;
    ArrayList<UUID> getChangedPatients(String odsCode, Date date) throws Exception;

    String getConditionsByPatientId(String odsCode, UUID patientId) throws Exception;
    String getAllergyIntolerancesByPatientId(String odsCode, UUID patientId) throws Exception;

    String getImmunizationsByPatientId(String odsCode, UUID patientId) throws Exception;

    String getMedicationPrescriptionsByPatientId(String odsCode, UUID patientId) throws Exception;

    String getAppointmentsForPatient(String odsCode, UUID patientId, Date dateFrom, Date dateTo) throws Exception;
    String getSchedules(String odsCode, Date dateFrom, Date dateTo, UUID practitionerId) throws Exception;
    String getSlots(String odsCode, String scheduleId) throws Exception;
    Boolean bookSlot(String odsCode, String slotId, UUID patientId) throws Exception;
    Boolean cancelSlot(String odsCode, String slotId, UUID patientId) throws Exception;
}