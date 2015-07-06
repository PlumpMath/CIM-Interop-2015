package org.endeavourhealth.cim.dataManager;

import org.apache.camel.util.ResourceHelper;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.adapter.MockDataAdapter;
import org.endeavourhealth.cim.common.BundleHelper;
import org.endeavourhealth.cim.common.FhirFilterHelper;
import org.endeavourhealth.cim.common.ReferenceHelper;
import org.endeavourhealth.cim.common.TextUtils;
import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenTransformer;
import org.endeavourhealth.cim.transform.openhr.OpenHRTransformer;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomuserdetails.UserDetails;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.*;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class EmisDataManager implements IDataManager {
	protected IDataAdapter _emisDataAdapter = new MockDataAdapter();
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
		// Get the relevant data adapter from the factory
		String appointmentDataInNativeFormat = _emisDataAdapter.getAppointmentsForPatient(odsCode, patientId, dateFrom, dateTo);

		// Get patientApi data transformer for service (native format -> FHIR)
		Bundle appointments = _emisOpenTransformer.toFHIRAppointmentBundle(patientId.toString(), appointmentDataInNativeFormat);

		// Get practitioner user ids from appointment bundle
		ArrayList<String> userIds = new ArrayList<String>();

		BundleHelper
				.getResourcesOfType(appointments, Appointment.class)
				.forEach(t -> userIds.addAll(
							t
							.getParticipant()
							.stream()
							.map(s -> ReferenceHelper.getReferenceId(s.getActor(), Practitioner.class))
							.filter(s -> !TextUtils.isNullOrTrimmedEmpty(s))
							.distinct()
							.collect(Collectors.toCollection(ArrayList<String>::new))));

		// Create mapping between user ids and guids
		HashMap<String, String> userIdGuidMap = new HashMap<String, String>();

		for (String userId : userIds)
			userIdGuidMap.put(userId, getUserGuidById(odsCode, Integer.parseInt(userId)));

		// set user guids back to appointment bundle
		BundleHelper
				.getResourcesOfType(appointments, Appointment.class)
				.forEach(t ->
						t
						.getParticipant()
						.forEach(s -> ReferenceHelper.updateReferenceFromMap(s.getActor(), Practitioner.class, userIdGuidMap)));

		return new JsonParser().composeString(appointments);
	}

	private String getUserGuidById(String odsCode, int userId) throws Exception {
		String userXml = _emisDataAdapter.getUserById(odsCode, Integer.toString(userId));

		if (TextUtils.isNullOrTrimmedEmpty(userXml))
			return null;

		UserDetails userDetails = TransformHelper.unmarshall(userXml, UserDetails.class);

		return userDetails.getPerson().getGUID();
	}

	@Override
	public void requestOrder(String odsCode, String orderRequestData) throws Exception {
		Order orderRequest = (Order)new JsonParser().parse(orderRequestData);

		// Get data transformer for service
		String orderRequestInNativeFormat = ""; //transformer.fromFHIROrder(orderRequest);

		// Get patient data by NHS Number
		_emisDataAdapter.requestOrder(odsCode, orderRequestInNativeFormat);
	}

	@Override
	public String getSchedules(String odsCode, Date dateFrom, Date dateTo) throws Exception {
		String schedulesInNativeFormat = _emisDataAdapter.getSchedules(odsCode, dateFrom, dateTo);
		Bundle bundle = _emisOpenTransformer.toFHIRScheduleBundle(schedulesInNativeFormat);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getSchedules(String odsCode, String actor) throws Exception {
		String schedulesInNativeFormat = _emisDataAdapter.getSchedules(odsCode, actor);
		Bundle bundle = _emisOpenTransformer.toFHIRScheduleBundle(schedulesInNativeFormat);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getSlots(String odsCode, String scheduleId) throws Exception {
		String slotsInNativeFormat = _emisDataAdapter.getSlots(odsCode, scheduleId);
		Bundle bundle = _emisOpenTransformer.toFHIRSlotBundle(scheduleId, slotsInNativeFormat);
		return new JsonParser().composeString(bundle);

	}
}
