package org.endeavourhealth.cim.dataManager.emis;

import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.common.DateUtils;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.exceptions.CIMPrincipalSystemException;

import javax.xml.soap.*;
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

        return getSOAPResult(responseMessage, soapMethod);
    }

    public String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber) throws Exception {

        final String soapMethod = "GetPatientDemographicsByNhsNumber";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("nhsNumber", nhsNumber);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResult(responseMessage, soapMethod);
    }

    public String getPatientDemographicsByPatientId(String odsCode, UUID patientId) throws Exception {

        final String soapMethod = "GetPatientDemographics";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("patientGuid", patientId.toString());

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResult(responseMessage, soapMethod);
    }

    public String tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) throws Exception {

        final String soapMethod = "TracePatientByDemographics";

        Map<String, String> parameters = createParameterMap();
        parameters.put("surname", surname);
        parameters.put("sex", gender.substring(0, 1).toUpperCase());
        parameters.put("dateOfBirth", new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth));
        parameters.put("forename", forename);
        parameters.put("postcode", postcode);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResult(responseMessage, soapMethod);
    }

    public String tracePatientByNhsNumber(String nhsNumber) throws Exception {

        final String soapMethod = "TracePatientByNhsNumber";

        Map<String, String> parameters = createParameterMap();
        parameters.put("nhsNumber", nhsNumber);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResult(responseMessage, soapMethod);
    }

    public ArrayList<UUID> getChangedPatients(String odsCode, Date date) throws Exception {

        final String soapMethod = "GetChangedPatients";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("sinceDateTime", DateUtils.formatDateAsISO8601(date));

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        String responseText = getSOAPResult(responseMessage, soapMethod);

        ArrayList<UUID> uuids = new ArrayList<>();
        if (!responseText.isEmpty())
            for (String uuidString : responseText.split("(?<=\\G.{36})"))
                uuids.add(UUID.fromString(uuidString));

        return uuids;
    }

    // Medical record
    public String createCondition(String odsCode, String openHRXml) throws Exception {

        final String soapMethod = "UpdatePatient";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("openHRXml", openHRXml);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResult(responseMessage, soapMethod);
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

        return getSOAPResult(responseMessage, soapMethod);
    }

    public Boolean bookSlot(String odsCode, String slotId, UUID patientId, String reason) throws Exception {

        final String soapMethod = "BookAppointment";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("slotId", slotId);
        parameters.put("patientGuid", patientId.toString());
        parameters.put("reason", reason);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        getSOAPResult(responseMessage, soapMethod);
        return true;
    }

    public Boolean cancelSlot(String odsCode, String slotId, UUID patientId) throws Exception {

        final String soapMethod = "CancelAppointment";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("slotId", slotId);
        parameters.put("patientGuid", patientId.toString());

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        getSOAPResult(responseMessage, soapMethod);
        return true;
    }

    public String getSchedules(String odsCode, Date dateFrom, Date dateTo) throws Exception {

        final String soapMethod = "GetAppointmentSessions";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("fromDate", DateUtils.formatDateAsISO8601(dateFrom));
        parameters.put("toDate", DateUtils.formatDateAsISO8601(dateTo));

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResult(responseMessage, soapMethod);
    }

    public String getSlots(String odsCode, String scheduleId) throws Exception {

        final String soapMethod = "GetSlotsForSession";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);
        parameters.put("sessionId", scheduleId);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResult(responseMessage, soapMethod);
    }

    public String getOrganisationInformation(String odsCode) throws Exception {

        final String soapMethod = "GetOrganisationInformation";

        Map<String, String> parameters = createParameterMap();
        parameters.put("odsCode", odsCode);

        SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

        return getSOAPResult(responseMessage, soapMethod);
    }

    // Utility methods
    private static Map<String, String> createParameterMap() {
        return new LinkedHashMap<>();
    }

    private static SOAPMessage createSOAPMessage(String soapMethod, Map<String, String> parameters) throws Exception {

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader(HeaderKey.SOAPAction, _actionUri + "/" + soapMethod);

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

    private static String getSOAPResult(SOAPMessage soapResponse, String soapMethod) throws Exception {

        if (soapResponse.getSOAPBody().hasFault())
            throw new CIMPrincipalSystemException("SOAP fault received from principal system.\r\n\r\n" + soapResponse.getSOAPBody().getFault().getFaultString());

        return soapResponse.getSOAPBody().getElementsByTagName(soapMethod + "Result").item(0).getTextContent();
    }
}
