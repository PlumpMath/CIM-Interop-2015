package org.endeavourhealth.cim.adapter;

import javax.xml.soap.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class MockDataAdapter implements IDataAdapter {
    private final String _soapUri = "http://localhost:9001/GPApiService/Soap";
    private final String _actionUri = "http://tempuri.org/IGPApiService";

    // System
    public String getTransformerTypeName() {
        return "org.endeavourhealth.cim.transform.openhr.OpenHRTransformer";
    }

    // Demographics
    public String getPatientRecordByPatientId(String odsCode, UUID patientId) {
        SOAPConnection soapConnection = null;
        try {
            try {
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("GetPatient");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("GetPatient", "", "http://tempuri.org/");

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
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("GetPatientDemographics");

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
                String soapMethodName = "TracePatientByDemographics";

                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest(soapMethodName);

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement(soapMethodName, "", "http://tempuri.org/");
                SOAPElement surnameElement = soapMethodElement.addChildElement("surname");
                surnameElement.addTextNode(surname);
                SOAPElement sexElement = soapMethodElement.addChildElement("sex");
                sexElement.addTextNode(gender.substring(0, 1).toUpperCase());
                SOAPElement dobElement = soapMethodElement.addChildElement("dateOfBirth");
                dobElement.addTextNode(new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth));
                soapMethodElement.addChildElement("forename");
                soapMethodElement.addChildElement("postcode");
                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/" + soapMethodName);
                return soapResponse.getSOAPBody().getElementsByTagName(soapMethodName + "Result").item(0).getTextContent();
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
                String soapMethodName = "TracePatientByNhsNumber";

                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest(soapMethodName);

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement(soapMethodName, "", "http://tempuri.org/");
                SOAPElement nhsNumberElement = soapMethodElement.addChildElement("nhsNumber");
                nhsNumberElement.addTextNode(nhsNumber);
                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/" + soapMethodName);
                return soapResponse.getSOAPBody().getElementsByTagName(soapMethodName + "Result").item(0).getTextContent();
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
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("GetChangedPatients");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("GetChangedPatients", "", "http://tempuri.org/");

                createChildTextElement(soapMethodElement, "odsCode", odsCode);
                createChildTextElement(soapMethodElement, "sinceDateTime", (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")).format(date));

                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetChangedPatients");
                String responseText = soapResponse.getSOAPBody().getElementsByTagName("GetChangedPatientsResult").item(0).getTextContent();

                ArrayList<UUID> uuids = new ArrayList<>();
                if (responseText.isEmpty() == false)
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
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("UpdatePatient");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("UpdatePatient", "", "http://tempuri.org/");

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
    public String getAppointmentsForPatient(String odsCode, UUID patientId) {
        return null;
    }
    public void requestOrder(String odsCode, String orderRequest) {

    }
    public String getSchedules(String odsCode) {
        return null;
    }
    public String getSlots(String odsCode, UUID scheduleId) {
        return null;
    }

    // Utility methods
    private static void createChildTextElement(SOAPElement element, String childElementName, String childElementValue) throws javax.xml.soap.SOAPException {
        SOAPElement childElement = element.addChildElement(childElementName);
        childElement.addTextNode(childElementValue);
    }
    private SOAPMessage createSOAPRequest(String methodCall) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", _actionUri + "/" + methodCall);

        return soapMessage;
    }
}
