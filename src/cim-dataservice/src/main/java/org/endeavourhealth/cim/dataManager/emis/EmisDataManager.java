package org.endeavourhealth.cim.dataManager.emis;

import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.common.exceptions.CIMInvalidInternalIdentifier;
import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.common.text.TextUtils;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.common.FhirFilterHelper;
import org.endeavourhealth.cim.transform.EmisTransformer;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.*;

import java.util.*;

@SuppressWarnings("unused")
public class EmisDataManager implements IDataManager {

	protected EmisDataAdapter _emisDataAdapter = new EmisDataAdapter();
	private final EmisTransformer _emisTransformer = new EmisTransformer();

	/* administrative */

	@Override
	public String getSchedules(String odsCode, Date dateFrom, Date dateTo, String practitionerId) throws Exception {

		UUID practitionerUuid = null;
		if (practitionerId != null)
			try {
				practitionerUuid = UUID.fromString(practitionerId);
			} catch (IllegalArgumentException e) {
				throw new CIMInvalidInternalIdentifier("Practitioner Id");
			}

		String schedulesXml = _emisDataAdapter.getSchedules(odsCode, dateFrom, dateTo);
		String organisationXml = _emisDataAdapter.getOrganisationInformation(odsCode);
		BundleProperties bundleProperties = getBundleProperties(odsCode);

		Bundle bundle = _emisTransformer.eopenToFhirScheduleBundle(bundleProperties, schedulesXml, organisationXml);
		bundle = FhirFilterHelper.filterScheduleByPractitioner(bundle, practitionerUuid);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getSlots(String odsCode, String scheduleId) throws Exception {

		String slotsXml = _emisDataAdapter.getSlots(odsCode, scheduleId);
		BundleProperties bundleProperties = getBundleProperties(odsCode);

		Bundle bundle = _emisTransformer.eopenToFhirSlotBundle(bundleProperties, scheduleId, slotsXml);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String getAppointmentsForPatient(String odsCode, String patientId, Date dateFrom, Date dateTo) throws Exception {
		UUID patientUuid;
		try {
			patientUuid = UUID.fromString(patientId);
		} catch (IllegalArgumentException e) {
			throw new CIMInvalidInternalIdentifier("Patient Id");
		}

		String appointmentDataInNativeFormat = _emisDataAdapter.getAppointmentsForPatient(odsCode, patientUuid, dateFrom, dateTo);
		String organisationXml = _emisDataAdapter.getOrganisationInformation(odsCode);
		BundleProperties bundleProperties = getBundleProperties(odsCode);

		Bundle bundle = _emisTransformer.eopenToFhirAppointmentForPatientBundle(bundleProperties, patientUuid.toString(), appointmentDataInNativeFormat, organisationXml);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String bookSlot(String odsCode, String slotId, String patientId) throws Exception {
		UUID patientUuid;
		try {
			patientUuid = UUID.fromString(patientId);
		} catch (IllegalArgumentException e) {
			throw new CIMInvalidInternalIdentifier("Patient Id");
		}

		return _emisDataAdapter.bookSlot(odsCode, slotId, patientUuid, "");
	}

	public String cancelSlot(String odsCode, String slotId, String patientId) throws Exception {
		UUID patientUuid;
		try {
			patientUuid = UUID.fromString(patientId);
		} catch (IllegalArgumentException e) {
			throw new CIMInvalidInternalIdentifier("Patient Id");
		}

		return _emisDataAdapter.cancelSlot(odsCode, slotId, patientUuid);
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
			throw new CIMInvalidInternalIdentifier("Patient Id");
		}

		String openHRXml = _emisDataAdapter.getPatientRecordByPatientId(odsCode, patientUuid);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			return null;

		return _emisTransformer.openHRToFhirBundle(getBundleProperties(odsCode), openHRXml);
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
	public String addCondition(String odsCode, String fhirRequest) throws Exception {

		Condition condition = (Condition)new JsonParser().parse(fhirRequest);
		String request = _emisTransformer.fromFHIRCondition(condition);
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
	public String getMedicationPrescriptions(String odsCode, String patientId) throws Exception {

		Bundle bundle = getPatientRecordAsBundle(odsCode, patientId);

		if (bundle == null)
			return null;

		bundle = FhirFilterHelper.getMedicationPrescriptions(bundle);
		return new JsonParser().composeString(bundle);
	}


	/* demographic */

	@Override
	public String getPatientDemographics(String odsCode, String patientId) throws Exception {
		UUID patientUuid;
		try {
			patientUuid = UUID.fromString(patientId);
		} catch (IllegalArgumentException e) {
			throw new CIMInvalidInternalIdentifier("Patient Id");
		}

		String openHRXml = _emisDataAdapter.getPatientDemographicsByPatientId(odsCode, patientUuid);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			return null;

		Patient patient = _emisTransformer.openHRToFhirPatient(openHRXml);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String getPatientDemographicsByNhsNumber(String odsCode, String nhsNumber) throws Exception {

		String openHRXml = _emisDataAdapter.getPatientDemographicsByNHSNumber(odsCode, nhsNumber);

		if (TextUtils.isNullOrTrimmedEmpty(openHRXml))
			return null;

		Patient patient = _emisTransformer.openHRToFhirPatient(openHRXml);
		return new JsonParser().composeString(patient);
	}

	@Override
	public String tracePersonByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception {

		List<String> openHRXml = _emisDataAdapter.tracePatientByDemographics(surname, dateOfBirth, gender, forename, postcode);

		Bundle bundle = _emisTransformer.openHRToFhirPersonBundle(openHRXml);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public String tracePersonByNhsNumber(String nhsNumber) throws Exception {

		List<String> openHRXml = _emisDataAdapter.tracePatientByNhsNumber(nhsNumber);

		Bundle bundle = _emisTransformer.openHRToFhirPersonBundle(openHRXml);
		return new JsonParser().composeString(bundle);
	}

	@Override
	public List<String> getChangedPatients(String odsCode, Date date) throws Exception {
		List<UUID> patientUuids = _emisDataAdapter.getChangedPatients(odsCode, date);

		List<String> patientIds = new ArrayList<>();
		for (UUID patientUuid : patientUuids)
			patientIds.add(patientUuid.toString());

		return patientIds;
	}

	@Override
	public String getUser(String odsCode, String userId) throws Exception {
		UUID userUuid;
		try {
			userUuid = UUID.fromString(userId);
		} catch (IllegalArgumentException e) {
			throw new CIMInvalidInternalIdentifier("User Id");
		}

		String openHRXml = _emisDataAdapter.getUserById(odsCode, userUuid);
		Person person = _emisTransformer.openHRToFhirPerson(openHRXml);

		return new JsonParser().composeString(person);
	}

	@Override
	public String getOrganisation(String odsCode) throws Exception {
		String openHRXml = _emisDataAdapter.getOrganisationById(odsCode);
		Organization organisation = _emisTransformer.openHRToFhirOrganisation(openHRXml);

		return new JsonParser().composeString(organisation);
	}

	@Override
	public String getLocation(String odsCode, String locationId) throws Exception {
		UUID locationUuid;
		try {
			locationUuid = UUID.fromString(locationId);
		} catch (IllegalArgumentException e) {
			throw new CIMInvalidInternalIdentifier("Location Id");
		}

		String openHRXml = _emisDataAdapter.getLocationById(odsCode, locationUuid);
		Location location = _emisTransformer.openHRToFhirLocation(openHRXml);

		return new JsonParser().composeString(location);
	}

	private BundleProperties getBundleProperties(String odsCode) {
		return new BundleProperties()
				.setBundleId(UUID.randomUUID().toString())
				.setBaseUri(Registry.Instance().getBaseUri(odsCode))
				.setBundleType(Bundle.BundleType.SEARCHSET);
	}
}
