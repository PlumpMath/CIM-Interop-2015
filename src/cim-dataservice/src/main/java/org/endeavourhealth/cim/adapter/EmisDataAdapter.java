package org.endeavourhealth.cim.adapter;

import org.endeavourhealth.cim.common.DateUtils;
import org.endeavourhealth.cim.common.HeaderKey;

import javax.xml.soap.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmisDataAdapter {
    private static final String _soapUri = "http://localhost:9001/GPApiService/Soap";
    private static final String _soapMethodUri = "http://tempuri.org/";
    private static final String _actionUri = "http://tempuri.org/IGPApiService";

    // System
    public String getTransformerTypeName() {
        return "org.endeavourhealth.cim.transform.EmisTransformer";
    }

    // Demographics
    public String getPatientRecordByPatientId(String odsCode, UUID patientId) {
        try {
            final String soapMethod = "GetPatient";

            Map<String, String> parameters = createParameterMap();
            parameters.put("odsCode", odsCode);
            parameters.put("patientGuid", patientId.toString());

            SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

            return getSOAPResult(responseMessage, soapMethod);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber) {
        try {
            final String soapMethod = "GetPatientDemographics";

            Map<String, String> parameters = createParameterMap();
            parameters.put("odsCode", odsCode);
            parameters.put("nhsNumber", nhsNumber);

            SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

            return getSOAPResult(responseMessage, soapMethod);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) {
        try {
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
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String tracePatientByNhsNumber(String nhsNumber) {
        try {
            final String soapMethod = "TracePatientByNhsNumber";

            Map<String, String> parameters = createParameterMap();
            parameters.put("nhsNumber", nhsNumber);

            SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

            return getSOAPResult(responseMessage, soapMethod);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<UUID> getChangedPatients(String odsCode, Date date) {
        try {
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
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Medical record
    public String createCondition(String odsCode, String openHRXml) {
        try {
            final String soapMethod = "UpdatePatient";

            Map<String, String> parameters = createParameterMap();
            parameters.put("odsCode", odsCode);
            parameters.put("openHRXml", openHRXml);

            SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

            return getSOAPResult(responseMessage, soapMethod);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getConditionsByPatientId(String odsCode, UUID patientId) {
        return getPatientRecordByPatientId(odsCode, patientId);
    }
    public String getAllergyIntolerancesByPatientId(String odsCode, UUID patientId) {
        return getPatientRecordByPatientId(odsCode, patientId);
    }
    public String getImmunizationsByPatientId(String odsCode, UUID patientId) {
        return getPatientRecordByPatientId(odsCode, patientId);
    }
    public String getMedicationPrescriptionsByPatientId(String odsCode, UUID patientId) {
        return getPatientRecordByPatientId(odsCode, patientId);
    }

    // Appointments
    public String getAppointmentsForPatient(String odsCode, UUID patientId, Date dateFrom, Date dateTo) {
        try {
            final String soapMethod = "GetPatientAppointments";

            Map<String, String> parameters = createParameterMap();
            parameters.put("odsCode", odsCode);
            parameters.put("patientGuid", patientId.toString());
            parameters.put("fromDate", DateUtils.formatDateAsISO8601(dateFrom));
            parameters.put("toDate", DateUtils.formatDateAsISO8601(dateTo));

            SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

            return getSOAPResult(responseMessage, soapMethod);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean bookSlot(String odsCode, String slotId, UUID patientId) {
        try {
            final String soapMethod = "BookAppointment";

            Map<String, String> parameters = createParameterMap();
            parameters.put("odsCode", odsCode);
            parameters.put("slotId", slotId);
            parameters.put("patientGuid", patientId.toString());
            parameters.put("reason", "");

            SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

            getSOAPResult(responseMessage, soapMethod);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean cancelSlot(String odsCode, String slotId, UUID patientId) {
        try {
            final String soapMethod = "CancelAppointment";

            Map<String, String> parameters = createParameterMap();
            parameters.put("odsCode", odsCode);
            parameters.put("slotId", slotId);
            parameters.put("patientGuid", patientId.toString());

            SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

            getSOAPResult(responseMessage, soapMethod);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSchedules(String odsCode, Date dateFrom, Date dateTo, String practitionerId) {
        try {
            final String soapMethod = "GetAppointmentSessions";

            Map<String, String> parameters = createParameterMap();
            parameters.put("odsCode", odsCode);
            parameters.put("fromDate", DateUtils.formatDateAsISO8601(dateFrom));
            parameters.put("toDate", DateUtils.formatDateAsISO8601(dateTo));

            SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

            return getSOAPResult(responseMessage, soapMethod);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSlots(String odsCode, String scheduleId) {
        try {
            final String soapMethod = "GetSlotsForSession";

            Map<String, String> parameters = createParameterMap();
            parameters.put("odsCode", odsCode);
            parameters.put("sessionId", scheduleId);

            SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

            return getSOAPResult(responseMessage, soapMethod);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getOrganisationInformation(String odsCode) {
        try {
            final String soapMethod = "GetOrganisationInformation";

            Map<String, String> parameters = createParameterMap();
            parameters.put("odsCode", odsCode);

            SOAPMessage responseMessage = performSOAPCall(soapMethod, parameters);

            return getSOAPResult(responseMessage, soapMethod);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
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

            return soapConnection.call(requestMessage, _soapUri + "/" + soapMethod);
        } finally {
            if (soapConnection != null)
                soapConnection.close();
        }
    }
    private static String getSOAPResult(SOAPMessage soapResponse, String soapMethod) throws Exception {
        return soapResponse.getSOAPBody().getElementsByTagName(soapMethod + "Result").item(0).getTextContent();
    }
}
