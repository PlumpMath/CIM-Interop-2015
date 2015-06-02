package org.endeavourhealth.cim.adapter;

import javax.xml.soap.*;
import java.io.*;
import java.util.UUID;

public class MockDataAdapter implements IDataAdapter {
    private final String _soapUri = "http://localhost:9001/GPApiService/Soap";
    private final String _actionUri = "http://tempuri.org/IGPApiService";

    public String getPatientByPatientId(UUID patientId) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("GetPatientResponse.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            return e.getMessage();
        }

        return sb.toString();
    }

    public String getPatientByNHSNumber(String nhsNumber) {
        SOAPConnection soapConnection = null;
        try {
            try {
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("GetCareRecord");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("GetCareRecord", "", "http://tempuri.org/");
                SOAPElement soapMethodParamElement1 = soapMethodElement.addChildElement("nhsNumber");
                soapMethodParamElement1.addTextNode(nhsNumber);
                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/GetCareRecord");
                return soapResponse.getSOAPBody().getElementsByTagName("GetCareRecordResult").item(0).getTextContent();
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

    public String createCondition(String requestData) {
        SOAPConnection soapConnection = null;
        try {
            try {
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create basic message
                SOAPMessage requestMessage = createSOAPRequest("UpdateCareRecord");

                // SOAP Body
                SOAPElement soapMethodElement = requestMessage.getSOAPBody().addChildElement("UpdateCareRecord", "", "http://tempuri.org/");
                SOAPElement soapMethodParamElement1 = soapMethodElement.addChildElement("nhsNumber");
                soapMethodParamElement1.addTextNode(requestData);
                requestMessage.saveChanges();

                // Send SOAP Message to SOAP Server
                SOAPMessage soapResponse = soapConnection.call(requestMessage, _soapUri + "/UpdateCareRecord");

                soapResponse.writeTo(System.out);

                return soapResponse.getSOAPBody().getElementsByTagName("GetCareRecordResult").item(0).getTextContent();

            } finally {
                if (soapConnection != null)
                    soapConnection.close();
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    private SOAPMessage createSOAPRequest(String methodCall) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", _actionUri + "/" + methodCall);

        return soapMessage;
    }
}
