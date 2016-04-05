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
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.*;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class EmisDataManager implements IDataManager
{
	protected EmisSoapClient _emisSoapClient = new EmisSoapClient();
	private final EmisOpenTransformer _emisOpenTransformer = new EmisOpenTransformer();
	private final OpenHRTransformer _openHRTransformer = new OpenHRTransformer();

	/* administrative */

	@Override
	public String searchSlots(String odsCode, Date dateFrom, Date dateTo, UUID locationId) throws Exception
	{
		// get schedules
		String schedulesXml = _emisSoapClient.getSessions(odsCode, dateFrom, dateTo);
		List<Schedule> schedules = _emisOpenTransformer.toFhirSchedules(schedulesXml);

		// if location is specified, filter by location
		if (locationId != null)
			schedules = FhirFilterHelper.filterScheduleByLocation(schedules, locationId);

		// get slots
		List<UUID> scheduleIds = FhirFilterHelper.getScheduleIds(schedules);

		String slotsXml = _emisSoapClient.getSlotsForSessions(odsCode, scheduleIds.toArray(new UUID[scheduleIds.size()]));
		List<Slot> slots = _emisOpenTransformer.toFhirSlots(slotsXml);

		// remove busy slots
		slots = FhirFilterHelper.removeBusySlots(slots);

		// remove schedules with no slots
		schedules = FhirFilterHelper.removeSchedulesWithNoSlots(schedules, slots);

		// get practitioners
		List<UUID> practitionerIds = FhirFilterHelper.getPractitionerIds(schedules);
		List<Practitioner> practitioners = new ArrayList<>();

		for (UUID practitionerId : practitionerIds)
		{
			String practitionerXml = _emisSoapClient.getUser(odsCode, practitionerId);
			practitioners.addAll(_openHRTransformer.toFhirPractitioners(practitionerXml));
		}

		// get locations
		List<UUID> locationIds = FhirFilterHelper.getLocationIds(schedules);
		List<Location> locations = new ArrayList<>();

		for (UUID locationId2 : locationIds)
		{
			String locationXml = _emisSoapClient.getLocation(odsCode, locationId2);
			locations.add(_openHRTransformer.toFhirLocation(locationXml));
		}

		// create combined bundle
		List<Resource> resources = new ArrayList<>();
		resources.addAll(schedules);
		resources.addAll(slots);
		resources.addAll(practitioners);
		resources.addAll(locations);

		Bundle bundle = BundleHelper.createBundle(getBundleProperties(odsCode), resources);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String bookSlot(String odsCode, UUID slotId, UUID patientId) throws Exception
	{
		return _emisSoapClient.bookSlot(odsCode, slotId, patientId, "");
	}

	@Override
	public String cancelSlot(String odsCode, UUID slotId, UUID patientId) throws Exception
	{
		return _emisSoapClient.cancelSlot(odsCode, slotId, patientId);
	}

	@Override
	public String getPatientAppointments(String odsCode, UUID patientId, Appointment.AppointmentStatus status) throws Exception
	{
		String emisOpenAppointmentsXml = _emisSoapClient.getPatientAppointments(odsCode, patientId);
		List<Appointment> appointments = _emisOpenTransformer.toFhirAppointments(patientId.toString(), emisOpenAppointmentsXml);

		if (status != null)
			appointments = FhirFilterHelper.filterAppointmentsByStatus(appointments, status);

		Bundle bundle = BundleHelper.createBundle(getBundleProperties(odsCode), appointments);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getPractitioner(String odsCode, UUID practitionerId) throws Exception
	{
		String openHRXml = _emisSoapClient.getUser(odsCode, practitionerId);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			throw new NotFoundException();

		List<Practitioner> practitioners = _openHRTransformer.toFhirPractitioners(openHRXml);

		return new JsonParser().composeString(practitioners.get(0));
	}

	@Override
	public String getAllPractitioners(String odsCode) throws Exception
	{
		String openHRXml = _emisSoapClient.getAllUsers(odsCode);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			throw new NotFoundException();

		List<Practitioner> practitioners = _openHRTransformer.toFhirPractitioners(openHRXml);

		Bundle bundle = BundleHelper.createBundle(getBundleProperties(odsCode), practitioners);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String searchForOrganisationByOdsCode(String odsCode) throws Exception {
		String openHRXml = _emisSoapClient.getOrganisationByOdsCode(odsCode);

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

		String openHRXml = _emisSoapClient.getOrganisationById(organisationUuid);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			throw new NotFoundException("");

		Organization organisation = _openHRTransformer.toFhirOrganisation(openHRXml);

		return new JsonParser().composeString(organisation);
	}

	@Override
	public String getLocation(String odsCode, UUID locationId) throws Exception
	{
		String openHRXml = _emisSoapClient.getLocation(odsCode, locationId);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			throw new NotFoundException("");

		Location location = _openHRTransformer.toFhirLocation(openHRXml);

		return new JsonParser().composeString(location);
	}

	@Override
	public String getTask(String odsCode, UUID taskId) throws Exception
	{
		String openHRXml = _emisSoapClient.getTaskById(odsCode, taskId);
		Order task = _openHRTransformer.toFhirTask(openHRXml);

		return new JsonParser().composeString(task);
	}

	@Override
	public void addTask(String odsCode, String fhirRequest) throws Exception
	{
		Order task = (Order)new JsonParser().parse(fhirRequest);
		String request = _openHRTransformer.fromFhirTask(task);

		_emisSoapClient.addTask(odsCode, request);
	}

	@Override
	public String getUserTasks(String odsCode, UUID userId) throws Exception
	{
		List<String> openHRXml = _emisSoapClient.getTasksByUser(odsCode, userId);
		List<Order> tasks = _openHRTransformer.toFhirTasks(openHRXml);

		Bundle bundle = BundleHelper.createBundle(getBundleProperties(odsCode), tasks);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getOrganisationTasks(String odsCode) throws Exception
	{
		List<String> openHRXml = _emisSoapClient.getTasksByOrganisation(odsCode);
		List<Order> tasks = _openHRTransformer.toFhirTasks(openHRXml);

		Bundle bundle = BundleHelper.createBundle(getBundleProperties(odsCode), tasks);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getPatientTasks(String odsCode, UUID patientId) throws Exception
	{
		List<String> openHRXml = _emisSoapClient.getTasksByPatient(odsCode, patientId);
		List<Order> tasks = _openHRTransformer.toFhirTasks(openHRXml);

		Bundle bundle = BundleHelper.createBundle(getBundleProperties(odsCode), tasks);

		return new JsonParser().composeString(bundle);
	}

	/* clinical */

	@Override
	public String getAllergyIntolerances(String odsCode, UUID patientId) throws Exception
	{
		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		bundle = FhirFilterHelper.getAllergyIntolerances(bundle);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getFullRecord(String odsCode, UUID patientId) throws Exception
	{
		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		return new JsonParser().composeString(bundle);
	}

	private Bundle getPatientRecordAsBundle(String odsCode, UUID patientId) throws Exception
	{
		String openHRXml = _emisSoapClient.getPatientRecordByPatientId(odsCode, patientId);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			return null;

		List<Resource> resources = _openHRTransformer.toFhirFullRecord(openHRXml);

		return BundleHelper.createBundle(getBundleProperties(odsCode), resources);
	}

	@Override
	public String getConditions(String odsCode, UUID patientId) throws Exception
	{
		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		bundle = FhirFilterHelper.getConditions(bundle);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String addCondition(String odsCode, UUID patientId, String fhirRequest) throws Exception
	{
		String request;
		try
		{
			Condition condition = (Condition)new JsonParser().parse(fhirRequest);

			if ((condition.getPatient() == null) || (TextUtils.isNullOrTrimmedEmpty(condition.getPatient().getReference())))
				condition.setPatient(ReferenceHelper.createReference(ResourceType.Patient, patientId.toString()));

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

		String response = _emisSoapClient.createCondition(odsCode, request);
		String fhirResponse = response; // _openHrTransformer.toFHIRCondition(response));

		return fhirResponse;
	}

	@Override
	public String getImmunizations(String odsCode, UUID patientId) throws Exception
	{
		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		bundle = FhirFilterHelper.getImmunizations(bundle);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getMedicationPrescriptions(String odsCode, UUID patientId, MedicationOrder.MedicationOrderStatus medicationOrderStatus) throws Exception
	{
		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		bundle = FhirFilterHelper.getMedicationPrescriptions(bundle, medicationOrderStatus);

		return new JsonParser().composeString(bundle);
	}


	/* demographic */

	@Override
	public String getPatientDemographics(String odsCode, UUID patientId) throws Exception
	{
		String openHRXml = _emisSoapClient.getPatientDemographicsByPatientId(odsCode, patientId);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			return null;

		Patient patient = _openHRTransformer.toFhirPatient(openHRXml);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String getPatientDemographicsByNhsNumber(String odsCode, String nhsNumber) throws Exception
	{
		String openHRXml = _emisSoapClient.getPatientDemographicsByNHSNumber(odsCode, nhsNumber);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			return null;

		Patient patient = _openHRTransformer.toFhirPatient(openHRXml);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String tracePersonByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception {

		List<String> openHRXml = _emisSoapClient.tracePatientByDemographics(surname, dateOfBirth, gender, forename, postcode);
		List<Resource> patients = _openHRTransformer.toFhirPatients(openHRXml, true);

		Bundle bundle = BundleHelper.createBundle(getBundleProperties(""), patients);

		return new JsonParser().composeString(bundle);
	}

	@Override
	public String tracePersonByNhsNumber(String nhsNumber) throws Exception
	{
		List<String> openHRXml = _emisSoapClient.tracePatientByNhsNumber(nhsNumber);
		List<Resource> patients = _openHRTransformer.toFhirPatients(openHRXml, true);

		Bundle bundle = BundleHelper.createBundle(getBundleProperties(""), patients);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public List<String> getChangedPatientIds(String odsCode, Date date) throws Exception
	{
		List<UUID> patientUuids = _emisSoapClient.getChangedPatientIds(odsCode, date);

		return patientUuids
				.stream()
				.map(t -> t.toString())
				.collect(Collectors.toList());
	}

	@Override
	public String getChangedPatients(String odsCode, Date date) throws Exception
	{
		List<String> openHRXml = _emisSoapClient.getChangedPatients(odsCode, date);

		List<Resource> patients = _openHRTransformer.toFhirPatients(openHRXml, false);

		Bundle bundle = BundleHelper.createBundle(getBundleProperties(""), patients);
		return new JsonParser().composeString(bundle);
	}

	private BundleProperties getBundleProperties(String odsCode) {
		return new BundleProperties()
				.setBundleId(UUID.randomUUID().toString())
				.setBaseUri(Registry.Instance().getBaseUri(odsCode))
				.setBundleType(Bundle.BundleType.SEARCHSET);
	}
}
