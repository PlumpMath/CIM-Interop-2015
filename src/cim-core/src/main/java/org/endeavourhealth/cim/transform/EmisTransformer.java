package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenTransformer;
import org.endeavourhealth.cim.transform.openhr.OpenHRTransformer;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.*;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class EmisTransformer {

	private final OpenHRTransformer _openHRTransformer = new OpenHRTransformer();
	private final EmisOpenTransformer _emisOpenTransformer = new EmisOpenTransformer();

	/* administrative */
	public Bundle eopenToFhirScheduleBundle(BundleProperties bundleProperties, String eopenSchedulesXml, String eopenOrganisationXml) throws TransformException
	{

		return _emisOpenTransformer.toFhirScheduleBundle(bundleProperties, eopenSchedulesXml, eopenOrganisationXml);
	}

	public Bundle eopenToFhirSlotBundle(BundleProperties bundleProperties, String scheduleId, String eopenSlotsXml) throws TransformException {

		return _emisOpenTransformer.toFhirSlotBundle(bundleProperties, scheduleId, eopenSlotsXml);
	}

	public Bundle eopenToFhirAppointmentForPatientBundle(BundleProperties bundleProperties, String patientId, String eopenAppointmentsXml, String organisationXml) throws TransformException {

		return _emisOpenTransformer.toFhirAppointmentForPatientBundle(bundleProperties, patientId, eopenAppointmentsXml, organisationXml);
	}

	public Person openHRToFhirPerson(String openHRXml) throws TransformException {
		return _emisOpenTransformer.toFhirPerson(openHRXml);
	}

	public Organization openHRToFhirOrganisation(String openHRXml) throws TransformException {
		return _openHRTransformer.toFhirOrganisation(openHRXml);
	}

	public Location openHRToFhirLocation(String openHRXml) throws TransformException {
		return _openHRTransformer.toFhirLocation(openHRXml);
	}

	public Order openHRToFhirTask(String openHRXml) throws TransformException {
		return _openHRTransformer.toFhirTask(openHRXml);
	}

	public String fromFhirTask(Order task) throws TransformException {
		return _openHRTransformer.fromFhirTask(task);
	}

	/* clinical */
	public Bundle openHRToFhirBundle(BundleProperties bundleProperties, String openHRXml) throws TransformException {

		return _openHRTransformer.toFhirBundle(bundleProperties, openHRXml);
	}

	public String fromFHIRCondition(Condition condition) throws TransformException {

		return _openHRTransformer.fromFhirCondition(condition);
	}

	/* demographic */
	public Patient openHRToFhirPatient(String openHRXml) throws TransformException {

		return _openHRTransformer.toFhirPatient(openHRXml);
	}

	public Bundle openHRToFhirPersonBundle(List<String> openHRXmlArray) throws TransformException {

		return _openHRTransformer.toFhirPersonBundle(openHRXmlArray);
	}

	public Bundle openHRToFhirPatientBundle(List<String> openHRXmlArray) throws TransformException {

		return _openHRTransformer.toFhirPatientBundle(openHRXmlArray);
	}

	public Bundle openHRToFhirTaskBundle(List<String> openHRXmlArray) throws TransformException {
		return _openHRTransformer.toFhirTaskBundle(openHRXmlArray);
	}

	public String toFhirCareRecord(String openHRXml) throws Exception {
		BundleProperties properties = new BundleProperties()
				.setBundleId(UUID.randomUUID().toString());
		return new JsonParser().composeString(openHRToFhirBundle(properties, openHRXml));
	}

	public String fromFhirCareRecord(String fhirData) {
		return "<OPENHR REPRESENTATION OF FHIR RECORD>";
	}
}
