package org.endeavourhealth.cim.dataManager.emis;

import org.endeavourhealth.cim.camel.helpers.DateUtils;

import javax.xml.soap.SOAPMessage;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmisSoapClient extends DotNetSoapClient
{
    @Override
    protected String getActionUri()
    {
        return "http://tempuri.org/IGPApiService";
    }

    @Override
    protected String getSoapMethodUri()
    {
        return "http://tempuri.org/";
    }

    // Demographics
    public String getPatientRecordByPatientId(String odsCode, UUID patientId) throws Exception {

        final String soapMethod = "GetPatient";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("patientGuid", patientId.toString());

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber) throws Exception {

        final String soapMethod = "GetPatientDemographicsByNhsNumber";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("nhsNumber", nhsNumber);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String getPatientDemographicsByPatientId(String odsCode, UUID patientId) throws Exception {

        final String soapMethod = "GetPatientDemographics";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("patientGuid", patientId.toString());

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public List<String> tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception {

        final String soapMethod = "TracePatientByDemographics";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("surname", surname);
        parameters.put("sex", gender.substring(0, 1).toUpperCase());
        parameters.put("dateOfBirth", new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth));
        parameters.put("forename", forename);
        parameters.put("postcode", postcode);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsStringArray(responseMessage, soapMethod);
    }

    public List<String> tracePatientByNhsNumber(String nhsNumber) throws Exception {

        final String soapMethod = "TracePatientByNhsNumber";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("nhsNumber", nhsNumber);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsStringArray(responseMessage, soapMethod);
    }

    public List<UUID> getChangedPatientIds(String odsCode, Date date) throws Exception {

        final String soapMethod = "GetChangedPatientIds";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("sinceDateTime", (date != null) ? DateUtils.formatDateAsISO8601(date) : null);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsUUIDArray(responseMessage, soapMethod);
    }

    public List<String> getChangedPatients(String odsCode, Date date) throws Exception {

        final String soapMethod = "GetChangedPatients";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);

        if (date != null)
            parameters.put("sinceDateTime", DateUtils.formatDateAsISO8601(date));

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsStringArray(responseMessage, soapMethod);
    }

    // Medical record
    public String createCondition(String odsCode, String openHRXml) throws Exception {

        final String soapMethod = "UpdatePatient";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("openHRXml", openHRXml);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    // Appointments
    public String getPatientAppointments(String odsCode, UUID patientId) throws Exception
    {
        final String soapMethod = "GetPatientAppointments";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("patientGuid", patientId.toString());
        parameters.put("fromDate", DateUtils.formatDateAsISO8601(DateUtils.DOTNET_MINIMUM_DATE));
        parameters.put("toDate", DateUtils.formatDateAsISO8601(DateUtils.DOTNET_MAXIMUM_DATE));

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String bookSlot(String odsCode, UUID slotId, UUID patientId, String reason) throws Exception {

        final String soapMethod = "BookAppointment2";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("slotGuid", slotId.toString());
        parameters.put("patientGuid", patientId.toString());
        parameters.put("reason", reason);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String cancelSlot(String odsCode, UUID slotId, UUID patientId) throws Exception {

        final String soapMethod = "CancelAppointment2";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("slotGuid", slotId.toString());
        parameters.put("patientGuid", patientId.toString());

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String getSessions(String odsCode, Date dateFrom, Date dateTo) throws Exception {

        final String soapMethod = "GetAppointmentSessions";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("fromDate", DateUtils.formatDateAsISO8601(dateFrom));
        parameters.put("toDate", DateUtils.formatDateAsISO8601(dateTo));

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String getSlotsForSessions(String odsCode, UUID[] sessionIds) throws Exception {

        final String soapMethod = "GetSlotsForSessions";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("sessionIds", sessionIds);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String getOrganisationInformation(String odsCode) throws Exception {

        final String soapMethod = "GetOrganisationInformation";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

	// Admin
	public String getUser(String odsCode, UUID userId) throws Exception
    {
		final String soapMethod = "GetUser";

		Map<String, Object> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("userInRoleGuid", userId.toString());

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsString(responseMessage, soapMethod);
	}

    public String getAllUsers(String odsCode) throws Exception
    {
        final String soapMethod = "GetAllUsers";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

	public String getOrganisationByOdsCode(String odsCode) throws Exception {
		final String soapMethod = "GetOrganisationByOdsCode";

		Map<String, Object> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsString(responseMessage, soapMethod);
	}

    public String getOrganisationById(UUID organisationGuid) throws Exception {
        final String soapMethod = "GetOrganisationById";

        Map<String, Object> parameters = createParameterMap();
        parameters.put("organisationGuid", organisationGuid.toString());

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

	public String getLocation(String odsCode, UUID locationGuid) throws Exception
    {
		final String soapMethod = "GetLocation";

		Map<String, Object> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("locationGuid", locationGuid.toString());

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsString(responseMessage, soapMethod);
	}

	public String getTaskById(String odsCode, UUID taskId) throws Exception {
		final String soapMethod = "GetTask";

		Map<String, Object> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("taskGuid", taskId.toString());

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return  getSOAPResultAsString(responseMessage, soapMethod);
	}

	public void addTask(String odsCode, String task) throws Exception {
		final String soapMethod = "AddTask";

		Map<String, Object> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("openHRXml", task);

		performSOAPCall(soapMethod, parameters);

		// return  getSOAPResultAsString(responseMessage, soapMethod);
	}

	public List<String> getTasksByUser(String odsCode, UUID userId) throws Exception {
		final String soapMethod = "GetTasksByUserInRoleGuid";

		Map<String, Object> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("userInRoleGuid", userId.toString());

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsStringArray(responseMessage, soapMethod);
	}

	public List<String> getTasksByOrganisation(String odsCode) throws Exception {
		final String soapMethod = "GetTasksByOrganisation";

		Map<String, Object> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsStringArray(responseMessage, soapMethod);
	}

	public List<String> getTasksByPatient(String odsCode, UUID patientUuid) throws Exception {
		final String soapMethod = "GetTasksByPatientGuid";

		Map<String, Object> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("patientGuid", patientUuid.toString());

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsStringArray(responseMessage, soapMethod);	}
}
