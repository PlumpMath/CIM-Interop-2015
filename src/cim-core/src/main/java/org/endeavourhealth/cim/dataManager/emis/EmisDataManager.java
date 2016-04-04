package org.endeavourhealth.cim.dataManager.emis;

import org.endeavourhealth.cim.camel.exceptions.InvalidParamException;
import org.endeavourhealth.cim.dataManager.Registry;
import org.endeavourhealth.cim.camel.exceptions.InvalidInternalIdentifier;
import org.endeavourhealth.cim.transform.EmisOpenTransformer;
import org.endeavourhealth.cim.transform.OpenHRTransformer;
import org.endeavourhealth.cim.transform.common.BundleHelper;
import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.camel.exceptions.NotFoundException;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.camel.helpers.FhirFilterHelper;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.*;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class EmisDataManager implements IDataManager {

	protected EmisDataAdapter _emisDataAdapter = new EmisDataAdapter();
	private final EmisOpenTransformer _emisOpenTransformer = new EmisOpenTransformer();
	private final OpenHRTransformer _openHRTransformer = new OpenHRTransformer();

	/* administrative */

	@Override
	public String getSchedules(String odsCode, Date dateFrom, Date dateTo, String practitionerId) throws Exception {

		UUID practitionerUuid = null;
		if (practitionerId != null)
			try {
				practitionerUuid = UUID.fromString(practitionerId);
			} catch (IllegalArgumentException e) {
				throw new InvalidInternalIdentifier("Practitioner Id");
			}

		String schedulesXml = _emisDataAdapter.getSchedules(odsCode, dateFrom, dateTo);
		String organisationXml = _emisDataAdapter.getOrganisationInformation(odsCode);
		BundleProperties bundleProperties = getBundleProperties(odsCode);

		Bundle bundle = _emisOpenTransformer.toFhirScheduleBundle(bundleProperties, schedulesXml, organisationXml);
		bundle = FhirFilterHelper.filterScheduleByPractitioner(bundle, practitionerUuid);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getSlots(String odsCode, String scheduleId) throws Exception {

		String slotsXml = _emisDataAdapter.getSlots(odsCode, scheduleId);
		BundleProperties bundleProperties = getBundleProperties(odsCode);

		Bundle bundle = _emisOpenTransformer.toFhirSlotBundle(bundleProperties, scheduleId, slotsXml);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getAppointmentsForPatient(String odsCode, String patientId, Date dateFrom, Date dateTo) throws Exception {
		UUID patientUuid;
		try {
			patientUuid = UUID.fromString(patientId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("Patient Id");
		}

		String appointmentDataInNativeFormat = _emisDataAdapter.getAppointmentsForPatient(odsCode, patientUuid, dateFrom, dateTo);
		String organisationXml = _emisDataAdapter.getOrganisationInformation(odsCode);
		BundleProperties bundleProperties = getBundleProperties(odsCode);

		Bundle bundle = _emisOpenTransformer.toFhirAppointmentForPatientBundle(bundleProperties, patientUuid.toString(), appointmentDataInNativeFormat, organisationXml);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String bookSlot(String odsCode, String slotId, String patientId) throws Exception {
		UUID patientUuid;
		try {
			patientUuid = UUID.fromString(patientId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("Patient Id");
		}

		return _emisDataAdapter.bookSlot(odsCode, slotId, patientUuid, "");
	}

	public String cancelSlot(String odsCode, String slotId, String patientId) throws Exception {
		UUID patientUuid;
		try {
			patientUuid = UUID.fromString(patientId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("Patient Id");
		}

		return _emisDataAdapter.cancelSlot(odsCode, slotId, patientUuid);
	}

	@Override
	public String getUser(String odsCode, String userId) throws Exception {
		UUID userUuid;
		try {
			userUuid = UUID.fromString(userId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("User Id");
		}

		String openHRXml = _emisDataAdapter.getUserById(odsCode, userUuid);
		Person person = _emisOpenTransformer.toFhirPerson(openHRXml);

		return new JsonParser().composeString(person);
	}

	@Override
	public String searchForOrganisationByOdsCode(String odsCode) throws Exception {
		String openHRXml = _emisDataAdapter.getOrganisationByOdsCode(odsCode);

		ArrayList<Organization> organisations = new ArrayList<>();

		if (!TextUtils.isNullOrTrimmedEmpty(openHRXml))
			organisations.add(_openHRTransformer.toFhirOrganisation(openHRXml));

		Bundle bundle = BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, UUID.randomUUID().toString(), organisations);

		return new JsonParser().composeString(bundle);
	}

	public String getOrganisationById(String organisationId) throws Exception {
		UUID organisationUuid;
		try {
			organisationUuid = UUID.fromString(organisationId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("Organization Id");
		}

		String openHRXml = _emisDataAdapter.getOrganisationById(organisationUuid);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			throw new NotFoundException("");

		Organization organisation = _openHRTransformer.toFhirOrganisation(openHRXml);

		return new JsonParser().composeString(organisation);
	}

	@Override
	public String getLocation(String odsCode, String locationId) throws Exception {
		UUID locationUuid;
		try {
			locationUuid = UUID.fromString(locationId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("Location Id");
		}

		String openHRXml = _emisDataAdapter.getLocationById(odsCode, locationUuid);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			throw new NotFoundException("");

		Location location = _openHRTransformer.toFhirLocation(openHRXml);

		return new JsonParser().composeString(location);
	}

	@Override
	public String getTask(String odsCode, String taskId) throws Exception {
		UUID taskUuid;
		try {
			taskUuid = UUID.fromString(taskId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("Task Id");
		}

		String openHRXml = _emisDataAdapter.getTaskById(odsCode, taskUuid);
		Order task = _openHRTransformer.toFhirTask(openHRXml);

		return new JsonParser().composeString(task);
	}

	@Override
	public void addTask(String odsCode, String fhirRequest) throws Exception {
		Order task = (Order)new JsonParser().parse(fhirRequest);
		String request = _openHRTransformer.fromFhirTask(task);

		_emisDataAdapter.addTask(odsCode, request);
	}

	@Override
	public String getUserTasks(String odsCode, String userId) throws Exception {
		UUID userUuid;
		try {
			userUuid = UUID.fromString(userId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("User Id");
		}

		List<String> openHRXml = _emisDataAdapter.getTasksByUser(odsCode, userUuid);
		Bundle tasks = _openHRTransformer.toFhirTaskBundle(openHRXml);

		return new JsonParser().composeString(tasks);
	}

	@Override
	public String getOrganisationTasks(String odsCode) throws Exception{
		List<String> openHRXml = _emisDataAdapter.getTasksByOrganisation(odsCode);
		Bundle tasks = _openHRTransformer.toFhirTaskBundle(openHRXml);

		return new JsonParser().composeString(tasks);
	}

	@Override
	public String getPatientTasks(String odsCode, String patientId) throws Exception {
		UUID patientUuid;
		try {
			patientUuid = UUID.fromString(patientId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("Patient Id");
		}

		List<String> openHRXml = _emisDataAdapter.getTasksByPatient(odsCode, patientUuid);
		Bundle tasks = _openHRTransformer.toFhirTaskBundle(openHRXml);

		return new JsonParser().composeString(tasks);
	}

	/* clinical */

	@Override
	public String getFullRecord(String odsCode, String patientId) throws Exception {
		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		return new JsonParser().composeString(bundle);
	}

	private Bundle getPatientRecordAsBundle(String odsCode, String patientId) throws Exception {
		UUID patientUuid;
		try {
			patientUuid = UUID.fromString(patientId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("Patient Id");
		}

		String openHRXml = _emisDataAdapter.getPatientRecordByPatientId(odsCode, patientUuid);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			return null;

		return _openHRTransformer.toFhirBundle(getBundleProperties(odsCode), openHRXml);
	}

	@Override
	public String getConditions(String odsCode, String patientId) throws Exception {
		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		bundle = FhirFilterHelper.getConditions(bundle);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String addCondition(String odsCode, String patientId, String fhirRequest) throws Exception {

		UUID patientUuid;
		String request;
		try
		{
			try {
				patientUuid = UUID.fromString(patientId);
			}
			catch (Exception e){
				throw new InvalidParamException("patientId", e);
			}

			Condition condition = (Condition)new JsonParser().parse(fhirRequest);

			if ((condition.getPatient() == null) || (TextUtils.isNullOrTrimmedEmpty(condition.getPatient().getReference())))
				condition.setPatient(ReferenceHelper.createReference(ResourceType.Patient, patientId));

			if (!patientId.equals(ReferenceHelper.getReferenceId(condition.getPatient(), ResourceType.Patient)))
				throw new InvalidParamException("patientId");

			request = _openHRTransformer.fromFhirCondition(condition);
		}
		catch (InvalidParamException e1)
		{
			throw e1;
		}
		catch (Exception e2)
		{
			throw new InvalidParamException("condition", e2);
		}

		String response = _emisDataAdapter.createCondition(odsCode, request);
		String fhirResponse = response; // _openHrTransformer.toFHIRCondition(response));

		return fhirResponse;
	}

	@Override
	public String getAllergyIntolerances(String odsCode, String patientId) throws Exception {
		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		bundle = FhirFilterHelper.getAllergyIntolerances(bundle);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getImmunizations(String odsCode, String patientId) throws Exception {
		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		bundle = FhirFilterHelper.getImmunizations(bundle);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getMedicationPrescriptions(String odsCode, String patientId, MedicationOrder.MedicationOrderStatus medicationOrderStatus) throws Exception {

		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		bundle = FhirFilterHelper.getMedicationPrescriptions(bundle, medicationOrderStatus);

		return new JsonParser().composeString(bundle);
	}


	/* demographic */

	@Override
	public String getPatientDemographics(String odsCode, String patientId) throws Exception {
		UUID patientUuid;
		try {
			patientUuid = UUID.fromString(patientId);
		} catch (IllegalArgumentException e) {
			throw new InvalidInternalIdentifier("Patient Id");
		}

		String openHRXml = _emisDataAdapter.getPatientDemographicsByPatientId(odsCode, patientUuid);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			return null;

		Patient patient = _openHRTransformer.toFhirPatient(openHRXml);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String getPatientDemographicsByNhsNumber(String odsCode, String nhsNumber) throws Exception {

		String openHRXml = _emisDataAdapter.getPatientDemographicsByNHSNumber(odsCode, nhsNumber);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			return null;

		Patient patient = _openHRTransformer.toFhirPatient(openHRXml);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String tracePersonByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception {

		List<String> openHRXml = _emisDataAdapter.tracePatientByDemographics(surname, dateOfBirth, gender, forename, postcode);

		Bundle bundle = _openHRTransformer.toFhirPatientBundle(openHRXml, true);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String tracePersonByNhsNumber(String nhsNumber) throws Exception {

		List<String> openHRXml = _emisDataAdapter.tracePatientByNhsNumber(nhsNumber);

		Bundle bundle = _openHRTransformer.toFhirPatientBundle(openHRXml, true);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public List<String> getChangedPatientIds(String odsCode, Date date) throws Exception {
		List<UUID> patientUuids = _emisDataAdapter.getChangedPatientIds(odsCode, date);

		return patientUuids
				.stream()
				.map(t -> t.toString())
				.collect(Collectors.toList());
	}

	@Override
	public String getChangedPatients(String odsCode, Date date) throws Exception {
		List<String> openHRXml = _emisDataAdapter.getChangedPatients(odsCode, date);

		Bundle bundle = _openHRTransformer.toFhirPatientBundle(openHRXml, false);
		return new JsonParser().composeString(bundle);
	}

	private BundleProperties getBundleProperties(String odsCode) {
		return new BundleProperties()
				.setBundleId(UUID.randomUUID().toString())
				.setBaseUri(Registry.Instance().getBaseUri(odsCode))
				.setBundleType(Bundle.BundleType.SEARCHSET);
	}
}
