package org.endeavourhealth.cim.dataManager;

import org.hl7.fhir.instance.model.MedicationOrder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IDataManager {

    // administrative
    String searchSlots(String odsCode, Date dateFrom, Date dateTo, UUID practitionerId) throws Exception;
    String getAppointmentsForPatient(String odsCode, String patientId, Date dateFrom, Date dateTo) throws Exception;
    String bookSlot(String odsCode, String slotId, String patientId) throws Exception;
    String cancelSlot(String odsCode, String slotId, String patientId) throws Exception;
	String getUser(String odsCode, String userId) throws Exception;
	String searchForOrganisationByOdsCode(String odsCode) throws Exception;
    String getOrganisationById(String organisationId) throws Exception;
	String getLocation(String odsCode, String locationId) throws Exception;
	String getTask(String odsCode, String taskId) throws Exception;
	void addTask(String odsCode, String taskData) throws Exception;
	String getUserTasks(String odsCode, String userId) throws Exception;
	String getOrganisationTasks(String odsCode) throws Exception;
	String getPatientTasks(String odsCode, String patientId) throws Exception;

    // clinical
    String getFullRecord(String odsCode, String patientId) throws Exception;
    String getConditions(String odsCode, String patientId) throws Exception;
    String getAllergyIntolerances(String odsCode, String patientId) throws Exception;
    String getImmunizations(String odsCode, String patientId) throws Exception;
    String getMedicationPrescriptions(String odsCode, String patientId, MedicationOrder.MedicationOrderStatus medicationOrderStatus) throws Exception;
    String addCondition(String odsCode, String patientId, String request) throws Exception;

    // demographic
    String getPatientDemographics(String odsCode, String patientId) throws Exception;
    String getPatientDemographicsByNhsNumber(String odsCode, String nhsNumber) throws Exception;
    String tracePersonByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception;
    String tracePersonByNhsNumber(String nhsNumber) throws Exception;
    List<String> getChangedPatientIds(String odsCode, Date date) throws Exception;
    String getChangedPatients(String odsCode, Date date) throws Exception;
}