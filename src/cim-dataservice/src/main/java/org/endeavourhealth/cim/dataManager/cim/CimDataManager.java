package org.endeavourhealth.cim.dataManager.cim;

import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.endeavourhealth.common.core.HttpVerb;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.MedicationOrder;
import org.hl7.fhir.instance.model.Parameters;
import org.hl7.fhir.instance.model.StringType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CimDataManager implements IDataManager {
	@Override
	public String getSchedules(String odsCode, Date dateFrom, Date dateTo, String practitionerId) throws Exception {
		List<String> params = new ArrayList<>();
		if (dateFrom != null)
			params.add("date="+dateFrom.toString());
		if (dateTo != null)
			params.add("date="+dateTo.toString());
		if (practitionerId != null)
			params.add("actor:practitioner="+practitionerId);

		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Schedule", params, null);
	}

	@Override
	public String getSlots(String odsCode, String scheduleId) throws Exception {
		List<String> params = new ArrayList<>(Collections.singletonList("schedule=" + scheduleId));
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Slot", params, null);
	}

	@Override
	public String getAppointmentsForPatient(String odsCode, String patientId, Date dateFrom, Date dateTo) throws Exception {
		List<String> params = new ArrayList<>();
		if (patientId != null)
			params.add("patient="+patientId);
		if (dateFrom != null)
			params.add("date="+dateFrom.toString());
		if (dateTo != null)
			params.add("date="+dateTo.toString());

		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Appointment", params, null);
	}

	@Override
	public String bookSlot(String odsCode, String slotId, String patientId) throws Exception {
		Parameters param = new Parameters();
		param.addParameter().setName("patient").setValue(new StringType().setValue(patientId));

		String body = new JsonParser().composeString(param);
		return executeRequest(odsCode, HttpVerb.POST, odsCode + "/Slot/"+slotId+"/$book", null, body);
	}

	@Override
	public String cancelSlot(String odsCode, String slotId, String patientId) throws Exception {
		Parameters param = new Parameters();
		param.addParameter().setName("patient").setValue(new StringType().setValue(patientId));

		String body = new JsonParser().composeString(param);
		return executeRequest(odsCode, HttpVerb.POST, odsCode + "/Slot/"+slotId+"/$cancel", null, body);
	}

	@Override
	public String getUser(String odsCode, String userId) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/User/"+userId, null, null);
	}

	@Override
	public String getOrganisationByOdsCode(String odsCode) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode, null, null);
	}

	@Override
	public String getOrganisationById(String organisationId) throws Exception {
		return executeRequest(organisationId, HttpVerb.GET, organisationId, null, null);
	}

	@Override
	public String getLocation(String odsCode, String locationId) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Location/"+locationId, null, null);
	}

	@Override
	public String getTask(String odsCode, String taskId) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Task/"+taskId, null, null);
	}

	@Override
	public void addTask(String odsCode, String taskData) throws Exception {
		executeRequest(odsCode, HttpVerb.POST, odsCode + "/Task", null, taskData);
	}

	@Override
	public String getUserTasks(String odsCode, String userId) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/User/"+userId+"/Task", null, null);
	}

	@Override
	public String getOrganisationTasks(String odsCode) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Task", null, null);
	}

	@Override
	public String getPatientTasks(String odsCode, String patientId) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Patient/"+patientId+"/Task", null, null);
	}

	@Override
	public String getFullRecord(String odsCode, String patientId) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Patient/"+patientId+"/$everythingnobinary", null, null);
	}

	@Override
	public String getConditions(String odsCode, String patientId) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Patient/"+patientId+"/Condition", null, null);
	}

	@Override
	public String getAllergyIntolerances(String odsCode, String patientId) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Patient/"+patientId+"/AllergyIntolerance", null, null);
	}

	@Override
	public String getImmunizations(String odsCode, String patientId) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Patient/"+patientId+"/Immunization", null, null);
	}

	@Override
	public String getMedicationPrescriptions(String odsCode, String patientId, MedicationOrder.MedicationOrderStatus medicationOrderStatus) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Patient/"+patientId+"/MedicationPrescription", null, null);
	}

	@Override
	public String addCondition(String odsCode, String request) throws Exception {
		Condition condition = (Condition) new JsonParser().parse(request);
		String patientId = condition.getPatient().getId();
		return executeRequest(odsCode, HttpVerb.POST, odsCode + "/Patient/"+patientId+"/Condition", null, request);
	}

	@Override
	public String getPatientDemographics(String odsCode, String patientId) throws Exception {
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Patient/"+patientId, null, null);
	}

	@Override
	public String getPatientDemographicsByNhsNumber(String odsCode, String nhsNumber) throws Exception {
		List<String> params = new ArrayList<>(Collections.singletonList("identifier=urn:fhir.nhs.uk:id/NHSNumber" + nhsNumber));
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Patient", params, null);
	}

	@Override
	public String tracePersonByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception {
		List<String> params = new ArrayList<>();
		if (dateOfBirth != null)
			params.add("birthdate="+dateOfBirth.toString());
		if (gender != null)
			params.add("gender="+gender);
		if (forename != null)
			params.add("name="+forename);

		return executeRequest(null, HttpVerb.GET, "/Person/$trace", params, null);
	}

	@Override
	public String tracePersonByNhsNumber(String nhsNumber) throws Exception {
		List<String> params = new ArrayList<>(Collections.singletonList("identifier=urn:fhir.nhs.uk:id/NHSNumber" + nhsNumber));
		return executeRequest(null, HttpVerb.GET, "/Person/$trace", params, null);
	}

	@Override
	public List<String> getChangedPatientIds(String odsCode, Date date) throws Exception {
		List<String> params = new ArrayList<>(Collections.singletonList("_lastUpdated=" + date.toString()));
		String javaStringArray =  executeRequest(odsCode, HttpVerb.GET, odsCode + "/Patient", params, null);
		return (List<String>)new JsonParser().parse(javaStringArray);
	}

	@Override
	public String getChangedPatients(String odsCode, Date date) throws Exception {
		List<String> params = new ArrayList<>(Collections.singletonList("_lastUpdated=" + date.toString()));
		return executeRequest(odsCode, HttpVerb.GET, odsCode + "/Patient", params, null);
	}

	private HttpUriRequest generateRequest(String verb, String uri) {
		if (HttpVerb.GET.equals(verb))
			return new HttpGet(uri);

		if (HttpVerb.POST.equals(verb))
			return new HttpPost(uri);

		if (HttpVerb.PUT.equals(verb))
			return new HttpPut(uri);

		if (HttpVerb.DELETE.equals(uri))
			return new HttpDelete(uri);

		throw new IllegalArgumentException("Unknown Http verb " + verb);
	}

	private String executeRequest(String odsCode, String verb, String method, List<String> params, String body) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		CimConnectionData connectionData = getConnectionDataForService(odsCode);
		if (params != null && params.size() > 0) {
			method += "?" + String.join("&", params);
		}
		String uri = connectionData.getBaseUri() + method;

		HttpUriRequest request = generateRequest(verb, uri);

		HttpClient client = new DefaultHttpClient();

		String data = method;
		if (body != null)
			data += body;

		request.addHeader("api_key", connectionData.getApiKey());
		request.addHeader("hash", connectionData.getHash(data));

		HttpResponse response = client.execute(request);

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			throw new RuntimeException("Failed call : " + response.getStatusLine().getStatusCode());

		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String output = "";
		String line;
		while ((line = br.readLine()) != null)
			output += line;

		return output;
	}

	private CimConnectionData getConnectionDataForService(String odsCode) {
		return new CimConnectionData()
		.setBaseUri("cim-api.endeavourhealth.org/api/v0.1")
		.setApiKey("swagger")
		.setSecret("password");
	}
}
