package org.endeavourhealth.cim.dataManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public interface IDataManager {

    // administrative
    String getSchedules(String odsCode, Date dateFrom, Date dateTo, UUID practitionerId) throws Exception;
    String getSlots(String odsCode, String scheduleId) throws Exception;
    String getAppointmentsForPatient(String odsCode, UUID patientId, Date dateFrom, Date dateTo) throws Exception;
    String bookSlot(String odsCode, String slotId, UUID patientId) throws Exception;
    String cancelSlot(String odsCode, String slotId, UUID patientId) throws Exception;

    // clinical
    String getFullRecord(String odsCode, UUID patientId) throws Exception;
    String getConditions(String odsCode, UUID patientId) throws Exception;
    String getAllergyIntolerances(String odsCode, UUID patientId) throws Exception;
    String getImmunizations(String odsCode, UUID patientId) throws Exception;
    String getMedicationPrescriptions(String odsCode, UUID patientId) throws Exception;
    String addCondition(String odsCode, String request) throws Exception;

    // demographic
    String getPatientDemographics(String odsCode, UUID patientId) throws Exception;
    String getPatientDemographics(String odsCode, String nhsNumber) throws Exception;
    String tracePersonByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception;
    String tracePersonByNhsNumber(String nhsNumber) throws Exception;
    ArrayList<UUID> getChangedPatients(String odsCode, Date date) throws Exception;

}