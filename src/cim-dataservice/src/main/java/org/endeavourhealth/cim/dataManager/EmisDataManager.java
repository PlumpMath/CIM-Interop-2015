package org.endeavourhealth.cim.dataManager;

import org.endeavourhealth.cim.adapter.MockDataAdapter;
import org.endeavourhealth.cim.common.FhirFilterHelper;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenTransformer;
import org.endeavourhealth.cim.transform.openhr.OpenHRTransformer;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.*;

import java.util.*;

@SuppressWarnings("unused")
public class EmisDataManager implements IDataManager {
	protected MockDataAdapter _emisDataAdapter = new MockDataAdapter();
	private final OpenHRTransformer _openHrTransformer = new OpenHRTransformer();
	private final EmisOpenTransformer _emisOpenTransformer = new EmisOpenTransformer();

	@Override
	public String getPatientRecordByPatientId(String odsCode, UUID patientId) throws Exception {
		// Get patientApi data (native format) using adapter
		String patientData = _emisDataAdapter.getPatientRecordByPatientId(odsCode, patientId);

		// Get patientApi data transformer for service (native format -> FHIR)
		Bundle bundle = _openHrTransformer.toFHIRBundle(patientData);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber) throws Exception {
		// Get patientApi data (native format) using adapter
		String patientDataInNativeFormat = _emisDataAdapter.getPatientDemographicsByNHSNumber(odsCode, nhsNumber);

		// Get patientApi data transformer for service (native format -> FHIR)
		Patient patient = _openHrTransformer.toFHIRPatient(patientDataInNativeFormat);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception {
		// Get patientApi data (native format) using adapter
		String patientDataInNativeFormat = _emisDataAdapter.tracePatientByDemographics(surname, dateOfBirth, gender, forename, postcode);

		// Get patientApi data transformer for service (native format -> FHIR)
		Patient patient = _openHrTransformer.toFHIRPatient(patientDataInNativeFormat);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String tracePatientByNhsNumber(String nhsNumber) throws Exception {
		// Get patientApi data (native format) using adapter
		String patientDataInNativeFormat = _emisDataAdapter.tracePatientByNhsNumber(nhsNumber);

		// Get patientApi data transformer for service (native format -> FHIR)
		Patient patient = _openHrTransformer.toFHIRPatient(patientDataInNativeFormat);
		return new JsonParser().composeString(patient);	}

	@Override
	public String createCondition(String odsCode, String fhirRequest) throws Exception {
		Condition condition = (Condition)new JsonParser().parse(fhirRequest);

		String request = _openHrTransformer.fromFHIRCondition(condition);

		String response = _emisDataAdapter.createCondition(odsCode, request);

		String fhirResponse = response; // _openHrTransformer.toFHIRCondition(response));

		return fhirResponse;
	}

	@Override
	public ArrayList<UUID> getChangedPatients(String odsCode, Date date) {
		return _emisDataAdapter.getChangedPatients(odsCode, date);
	}

	@Override
	public String getConditionsByPatientId(String odsCode, UUID patientId) throws Exception {
		// Get patientApi data (native format) using adapter
		String patientData = _emisDataAdapter.getConditionsByPatientId(odsCode, patientId);

		// Get patientApi data transformer for service (native format -> FHIR)
		Bundle bundle = _openHrTransformer.toFHIRBundle(patientData);

		// Apply condition filter (in case supplier can only provide full record)
		bundle = FhirFilterHelper.getConditions(bundle);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getAllergyIntolerancesByPatientId(String odsCode, UUID patientId) throws Exception {

		// Get patientApi data (native format) using adapter
		String patientData = _emisDataAdapter.getAllergyIntolerancesByPatientId(odsCode, patientId);

		// Get patientApi data transformer for service (native format -> FHIR)
		Bundle bundle = _openHrTransformer.toFHIRBundle(patientData);

		// Apply allergy intolerance filter (in case supplier can only provide full record)
		bundle = FhirFilterHelper.getAllergyIntolerances(bundle);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getImmunizationsByPatientId(String odsCode, UUID patientId) throws Exception {
		// Get patientApi data (native format) using adapter
		String patientData = _emisDataAdapter.getImmunizationsByPatientId(odsCode, patientId);

		// Get patientApi data transformer for service (native format -> FHIR)
		Bundle bundle = _openHrTransformer.toFHIRBundle(patientData);

		// Apply immunization filter (in case supplier can only provide full record)
		bundle = FhirFilterHelper.getImmunizations(bundle);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getMedicationPrescriptionsByPatientId(String odsCode, UUID patientId) throws Exception {
		// Get patientApi data (native format) using adapter
		String patientData = _emisDataAdapter.getMedicationPrescriptionsByPatientId(odsCode, patientId);

		// Get patientApi data transformer for service (native format -> FHIR)
		Bundle bundle = _openHrTransformer.toFHIRBundle(patientData);

		// Apply medication prescriptions filter (in case supplier can only provide full record)
		bundle = FhirFilterHelper.getMedicationPrescriptions(bundle);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getAppointmentsForPatient(String odsCode, UUID patientId, Date dateFrom, Date dateTo) throws Exception {
		String appointmentDataInNativeFormat = _emisDataAdapter.getAppointmentsForPatient(odsCode, patientId, dateFrom, dateTo);
		String organisationXml = _emisDataAdapter.getOrganisationInformation(odsCode);

		Bundle bundle = _emisOpenTransformer.toFHIRAppointmentBundle(patientId.toString(), appointmentDataInNativeFormat, organisationXml);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public Boolean bookSlot(String odsCode, String slotId, UUID patientId) throws Exception {

		_emisDataAdapter.bookSlot(odsCode, slotId, patientId);

		return true;
	}

	@Override
	public String getSchedules(String odsCode, Date dateFrom, Date dateTo, String practitionerId) throws Exception {
		String schedulesXml = _emisDataAdapter.getSchedules(odsCode, dateFrom, dateTo, practitionerId);
		String organisationXml = _emisDataAdapter.getOrganisationInformation(odsCode);

		Bundle bundle = _emisOpenTransformer.toFHIRScheduleBundle(schedulesXml, organisationXml);

		bundle = FhirFilterHelper.filterScheduleByPractitioner(bundle, practitionerId);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getSlots(String odsCode, String scheduleId) throws Exception {
		String slotsXml = _emisDataAdapter.getSlots(odsCode, scheduleId);

		Bundle bundle = _emisOpenTransformer.toFHIRSlotBundle(scheduleId, slotsXml);

		return new JsonParser().composeString(bundle);
	}
}
