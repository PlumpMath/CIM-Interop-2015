package org.endeavourhealth.cim.dataManager.emis;

import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.common.core.DateUtils;
import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.common.core.exceptions.PrincipalSystemException;
import org.w3c.dom.Node;

import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmisDataAdapter {

    private static final String _soapMethodUri = "http://tempuri.org/";
    private static final String _actionUri = "http://tempuri.org/IGPApiService";

    // Demographics
    public String getPatientRecordByPatientId(String odsCode, UUID patientId) throws Exception {

        final String soapMethod = "GetPatient";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("patientGuid", patientId.toString());

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber) throws Exception {

        final String soapMethod = "GetPatientDemographicsByNhsNumber";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("nhsNumber", nhsNumber);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String getPatientDemographicsByPatientId(String odsCode, UUID patientId) throws Exception {

        final String soapMethod = "GetPatientDemographics";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("patientGuid", patientId.toString());

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public List<String> tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception {

        final String soapMethod = "TracePatientByDemographics";

        Map<String, String> parameters = createParameterMap();
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

        Map<String, String> parameters = createParameterMap();
        parameters.put("nhsNumber", nhsNumber);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsStringArray(responseMessage, soapMethod);
    }

    public List<UUID> getChangedPatients(String odsCode, Date date) throws Exception {

        final String soapMethod = "GetChangedPatients";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("sinceDateTime", DateUtils.formatDateAsISO8601(date));

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsUUIDArray(responseMessage, soapMethod);
    }

    // Medical record
    public String createCondition(String odsCode, String openHRXml) throws Exception {

        final String soapMethod = "UpdatePatient";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("openHRXml", openHRXml);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    // Appointments
    public String getAppointmentsForPatient(String odsCode, UUID patientId, Date dateFrom, Date dateTo) throws Exception {

        final String soapMethod = "GetPatientAppointments";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("patientGuid", patientId.toString());
        parameters.put("fromDate", DateUtils.formatDateAsISO8601(dateFrom));
        parameters.put("toDate", DateUtils.formatDateAsISO8601(dateTo));

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String bookSlot(String odsCode, String slotId, UUID patientId, String reason) throws Exception {

        final String soapMethod = "BookAppointment";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("slotId", slotId);
        parameters.put("patientGuid", patientId.toString());
        parameters.put("reason", reason);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String cancelSlot(String odsCode, String slotId, UUID patientId) throws Exception {

        final String soapMethod = "CancelAppointment";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("slotId", slotId);
        parameters.put("patientGuid", patientId.toString());

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String getSchedules(String odsCode, Date dateFrom, Date dateTo) throws Exception {

        final String soapMethod = "GetAppointmentSessions";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("fromDate", DateUtils.formatDateAsISO8601(dateFrom));
        parameters.put("toDate", DateUtils.formatDateAsISO8601(dateTo));

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String getSlots(String odsCode, String scheduleId) throws Exception {

        final String soapMethod = "GetSlotsForSession";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("sessionId", scheduleId);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

    public String getOrganisationInformation(String odsCode) throws Exception {

        final String soapMethod = "GetOrganisationInformation";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResultAsString(responseMessage, soapMethod);
    }

	// Admin
	public String getUserById(String odsCode, UUID userInRoleId) throws Exception {
		final String soapMethod = "GetUserByUserInRoleGuid";

		Map<String, String> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("userInRoleGuid", userInRoleId.toString());

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsString(responseMessage, soapMethod);
	}

	public String getOrganisationById(String odsCode) throws Exception {
		final String soapMethod = "GetOrganisation";

		Map<String, String> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsString(responseMessage, soapMethod);
	}

	public String getLocationById(String odsCode, UUID locationId) throws Exception {
		final String soapMethod = "GetLocation";

		Map<String, String> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("locationGuid", locationId.toString());

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsString(responseMessage, soapMethod);
	}

	public String getTaskById(String odsCode, UUID taskId) throws Exception {
		final String soapMethod = "GetTask";

		Map<String, String> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("taskGuid", taskId.toString());

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return  getSOAPResultAsString(responseMessage, soapMethod);
	}

	public void addTask(String odsCode, String task) throws Exception {
		final String soapMethod = "AddTask";

		Map<String, String> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("openHRXml", task);

		performSOAPCall(soapMethod, parameters);

		// return  getSOAPResultAsString(responseMessage, soapMethod);
	}

	public List<String> getTasksByUser(String odsCode, UUID userId) throws Exception {
		final String soapMethod = "GetTasksByUserInRoleGuid";

		Map<String, String> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("userInRoleGuid", userId.toString());

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsStringArray(responseMessage, soapMethod);
	}

	public List<String> getTasksByOrganisation(String odsCode) throws Exception {
		final String soapMethod = "GetTasksByOrganisation";

		Map<String, String> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsStringArray(responseMessage, soapMethod);
	}

	public List<String> getTasksByPatient(String odsCode, UUID patientUuid) throws Exception {
		final String soapMethod = "GetTasksByPatientGuid";

		Map<String, String> parameters = createParameterMap();
		parameters.put("odsCode", odsCode);
		parameters.put("patientGuid", patientUuid.toString());

		SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

		return getSOAPResultAsStringArray(responseMessage, soapMethod);	}

	// Utility methods
    private static Map<String, String> createParameterMap() {
        return new LinkedHashMap<>();
    }

    private static SOAPMessage createSOAPMessage(String soapMethod, Map<String, String> parameters) throws Exception {

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader(CIMHeaderKey.SOAPAction, _actionUri + "/" + soapMethod);

        SOAPElement soapMethodElement = soapMessage.getSOAPBody().addChildElement(soapMethod, "", _soapMethodUri);

        for (String key : parameters.keySet()) {
            SOAPElement childElement = soapMethodElement.addChildElement(key);

            if (parameters.get(key) != null)
                childElement.addTextNode(parameters.get(key));
        }

        soapMessage.saveChanges();

        return soapMessage;
    }

    private static SOAPMessage performSOAPCall(String soapMethod, Map<String, String> parameters) throws Exception {

        SOAPConnection soapConnection = null;
        try {
            SOAPMessage requestMessage = createSOAPMessage(soapMethod, parameters);

            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();

            return soapConnection.call(requestMessage, Registry.Instance().getEmisSoapUri() + "/" + soapMethod);
        } finally {
            if (soapConnection != null)
                soapConnection.close();
        }
    }

    private static List<String> getSOAPResultAsStringArray(SOAPMessage soapResponse, String soapMethod) throws Exception {

        return getSOAPResultAsArray(soapResponse, soapMethod, "string");
    }

    private static List<UUID> getSOAPResultAsUUIDArray(SOAPMessage soapResponse, String soapMethod) throws Exception {

        List<String> uuidsAsString = getSOAPResultAsArray(soapResponse, soapMethod, "guid");

        List<UUID> uuids = new ArrayList<>();

        for (String uuidString : uuidsAsString) {
            try {
                uuids.add(UUID.fromString(uuidString));
            } catch (Exception e) {
                throw new PrincipalSystemException("Unexpected result from principal system.", e);
            }
        }

        return uuids;
    }

    private static List<String> getSOAPResultAsArray(SOAPMessage soapResponse, String soapMethod, String typeName) throws Exception {

        Node soapResult = getSOAPResultAsElement(soapResponse, soapMethod);

        List<String> result = new ArrayList<>();

        for (int i = 0; i < soapResult.getChildNodes().getLength(); i++) {

            Node node = soapResult.getChildNodes().item(i);

            if (!node.getLocalName().equals(typeName))
                throw new PrincipalSystemException("Unexpected result from principal system.");

            result.add(node.getTextContent());
        }

        return result;
    }

    private static Node getSOAPResultAsElement(SOAPMessage soapResponse, String soapMethod) throws Exception {

        if (soapResponse.getSOAPBody().hasFault())
            throw new PrincipalSystemException("SOAP fault received from principal system.\r\n\r\n" + soapResponse.getSOAPBody().getFault().getFaultString());

        return soapResponse.getSOAPBody().getElementsByTagName(soapMethod + "Result").item(0);
    }

    private static String getSOAPResultAsString(SOAPMessage soapResponse, String soapMethod) throws Exception {

        return getSOAPResultAsElement(soapResponse, soapMethod).getTextContent();
    }

}
