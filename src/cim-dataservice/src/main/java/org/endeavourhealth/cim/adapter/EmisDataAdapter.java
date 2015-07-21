package org.endeavourhealth.cim.adapter;

import org.endeavourhealth.cim.common.DateUtils;
import org.endeavourhealth.cim.common.HeaderKey;

import javax.xml.soap.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class EmisDataAdapter {
    private final String _soapUri = "http://localhost:9001/GPApiService/Soap";
	// private final String _soapUri = "http://endeavour-gp.cloudapp.net:9001/GPApiService/Soap";
    private final String _actionUri = "http://tempuri.org/IGPApiService";

    // System
    public String getTransformerTypeName() {
        return "org.endeavourhealth.cim.transform.EmisTransformer";
    }

    // Demographics
    public String getPatientRecordByPatientId(String odsCode, UUID patientId) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("GetPatient");

                // SOAP Body
                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "GetPatient", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "patientGuid", patientId.toString());

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetPatient");
                return soapResponse.getSOAPBody().getElementsByTagName("GetPatientResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getPatientDemographicsByNHSNumber(String odsCode, String nhsNumber) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("GetPatientDemographics");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("GetPatientDemographics", "", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "nhsNumber", nhsNumber);

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetPatientDemographics");
                return soapResponse.getSOAPBody().getElementsByTagName("GetPatientDemographicsResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String tracePatientByDemographics(String surname, Date dateOfBirth, String gender, String forename, String postcode) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("TracePatientByDemographics");

                // SOAP Body
                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "TracePatientByDemographics", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "surname", surname);
                createChildTextElement(soapMethodElement, "sex", gender.substring(0, 1).toUpperCase());
                createChildTextElement(soapMethodElement, "dateOfBirth", new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth));
                createChildTextElement(soapMethodElement, "forename", null);
                createChildTextElement(soapMethodElement, "postcode", null);

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/TracePatientByDemographics");
                return soapResponse.getSOAPBody().getElementsByTagName("TracePatientByDemographicsResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String tracePatientByNhsNumber(String nhsNumber) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("TracePatientByNhsNumber");

                // SOAP Body
                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "TracePatientByNhsNumber", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "nhsNumber", nhsNumber);

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/" + "TracePatientByNhsNumber");
                return soapResponse.getSOAPBody().getElementsByTagName("TracePatientByNhsNumberResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<UUID> getChangedPatients(String odsCode, Date date) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("GetChangedPatients");

                // SOAP Body
                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "GetChangedPatients", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "sinceDateTime", DateUtils.formatDateAsISO8601(date));

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetChangedPatients");
                String responseText = soapResponse.getSOAPBody().getElementsByTagName("GetChangedPatientsResult").item(0).getTextContent();

                ArrayList<UUID> uuids = new ArrayList<>();
                if (!responseText.isEmpty())
                    for (String uuidString : responseText.split("(?<=\\G.{36})"))
                        uuids.add(UUID.fromString(uuidString));

                return uuids;

            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Medical record
    public String createCondition(String odsCode, String requestData) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("UpdatePatient");

                // SOAP Body
                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "UpdatePatient", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "openHRXml", requestData);

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/UpdatePatient");

                soapResponse.writeTo(System.out);

                return soapResponse.getSOAPBody().getElementsByTagName("UpdatePatientResult").item(0).getTextContent();

            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
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
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("GetPatientAppointments");

                // SOAP Body
                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "GetPatientAppointments", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "patientGuid", patientId.toString());
                createChildTextElement(soapMethodElement, "fromDate", DateUtils.formatDateAsISO8601(dateFrom));
                createChildTextElement(soapMethodElement, "toDate", DateUtils.formatDateAsISO8601(dateTo));

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetPatientAppointments");
                return soapResponse.getSOAPBody().getElementsByTagName("GetPatientAppointmentsResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean bookSlot(String odsCode, String slotId, UUID patientId) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("BookAppointment");

                // SOAP Body
                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "BookAppointment", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "slotId", slotId);
                createChildTextElement(soapMethodElement, "patientGuid", patientId.toString());
                createChildTextElement(soapMethodElement, "reason", "");

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/BookAppointment");
                return true; //soapResponse.getSOAPBody().getElementsByTagName("BookAppointmentResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean cancelSlot(String odsCode, String slotId, UUID patientId) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("CancelAppointment");

                // SOAP Body
                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "CancelAppointment", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "slotId", slotId);
                createChildTextElement(soapMethodElement, "patientGuid", patientId.toString());

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/CancelAppointment");
                return true; //soapResponse.getSOAPBody().getElementsByTagName("BookAppointmentResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSchedules(String odsCode, Date dateFrom, Date dateTo, String practitionerId) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("GetAppointmentSessions");

                // SOAP Body
                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "GetAppointmentSessions", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "fromDate", DateUtils.formatDateAsISO8601(dateFrom));
                createChildTextElement(soapMethodElement, "toDate", DateUtils.formatDateAsISO8601(dateTo));

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetAppointmentSessions");
                return soapResponse.getSOAPBody().getElementsByTagName("GetAppointmentSessionsResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSlots(String odsCode, String scheduleId) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequestMessage("GetSlotsForSession");

                // SOAP Body
                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "GetSlotsForSession", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "sessionId", scheduleId);

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetSlotsForSession");
                return soapResponse.getSOAPBody().getElementsByTagName("GetSlotsForSessionResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Admin
    public String getOrganisationInformation(String odsCode) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                SOAPMessage requestMessage = createSOAPRequestMessage("GetOrganisationInformation");

                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "GetOrganisationInformation", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);

                requestMessage.saveChanges();

                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetOrganisationInformation");
                return soapResponse.getSOAPBody().getElementsByTagName("GetOrganisationInformationResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String getUserById(String odsCode, String userId) {
        SOAPConnection soapConnection = null;
        try {
            try {
                soapConnection = createSOAPConnection();

                SOAPMessage requestMessage = createSOAPRequestMessage("GetUserByID");

                SOAPElement soapMethodElement = createSOAPMethodElement(requestMessage, "GetUserByID", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "userInRoleId", userId);

                requestMessage.saveChanges();

                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetUserByID");
                return soapResponse.getSOAPBody().getElementsByTagName("GetUserByIDResult").item(0).getTextContent();
            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // Utility methods
    private static SOAPConnection createSOAPConnection() throws SOAPException {
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        return soapConnectionFactory.createConnection();
    }
    private static SOAPElement createSOAPMethodElement(SOAPMessage message, String name, String namespace) throws SOAPException {
        return message.getSOAPBody().addChildElement(name, "", namespace);
    }
    private static void createChildTextElement(SOAPElement element, String childElementName, String childElementValue) throws javax.xml.soap.SOAPException {
        SOAPElement childElement = element.addChildElement(childElementName);

        if (childElementValue != null)
            childElement.addTextNode(childElementValue);
    }
    private SOAPMessage createSOAPRequestMessage(String methodCall) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader(HeaderKey.SOAPAction, _actionUri + "/" + methodCall);

        return soapMessage;
    }
}
