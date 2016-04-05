package org.endeavourhealth.cim.dataManager;

import org.hl7.fhir.instance.model.Appointment;
import org.hl7.fhir.instance.model.MedicationOrder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IDataManager
{
    // appointments
    String searchSlots(String odsCode, Date dateFrom, Date dateTo, UUID locationId) throws Exception;
    String bookSlot(String odsCode, UUID slotId, UUID patientId) throws Exception;
    String cancelSlot(String odsCode, UUID slotId, UUID patientId) throws Exception;
    String getPatientAppointments(String odsCode, UUID patientId, Appointment.AppointmentStatus status) throws Exception;

    // administrative
	String getPractitioner(String odsCode, UUID practitionerId) throws Exception;
    String getAllPractitioners(String odsCode) throws Exception;
	String searchForOrganisationByOdsCode(String odsCode) throws Exception;
    String getOrganisationById(String organisationId) throws Exception;
    String getLocation(String odsCode, UUID locationId) throws Exception;

    // tasks
    String getTask(String odsCode, UUID taskId) throws Exception;
	void addTask(String odsCode, String taskData) throws Exception;
	String getUserTasks(String odsCode, UUID userId) throws Exception;
	String getOrganisationTasks(String odsCode) throws Exception;
	String getPatientTasks(String odsCode, UUID patientId) throws Exception;

    // clinical
    String getFullRecord(String odsCode, UUID patientId) throws Exception;
    String getConditions(String odsCode, UUID patientId) throws Exception;
    String getAllergyIntolerances(String odsCode, UUID patientId) throws Exception;
    String getImmunizations(String odsCode, UUID patientId) throws Exception;
    String getMedicationPrescriptions(String odsCode, UUID patientId, MedicationOrder.MedicationOrderStatus medicationOrderStatus) throws Exception;
    String addCondition(String odsCode, UUID patientId, String request) throws Exception;

    // demographic
    String getPatientDemographics(String odsCode, UUID patientId) throws Exception;
    String getPatientDemographicsByNhsNumber(String odsCode, String nhsNumber) throws Exception;
    String tracePersonByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception;
    String tracePersonByNhsNumber(String nhsNumber) throws Exception;
    List<String> getChangedPatientIds(String odsCode, Date date) throws Exception;
    String getChangedPatients(String odsCode, Date date) throws Exception;
}