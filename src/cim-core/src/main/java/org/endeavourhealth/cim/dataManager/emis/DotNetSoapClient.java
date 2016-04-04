package org.endeavourhealth.cim.dataManager.emis;

import org.endeavourhealth.cim.camel.exceptions.PrincipalSystemException;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.Registry;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.util.*;

public abstract class DotNetSoapClient
{
    protected abstract String getActionUri();
    protected abstract String getSoapMethodUri();

    protected Map<String, Object> createParameterMap() {
        return new LinkedHashMap<>();
    }

    protected SOAPMessage createSOAPMessage(String soapMethod, Map<String, Object> parameters) throws Exception {

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader(CIMHeaderKey.SOAPAction, getActionUri() + "/" + soapMethod);

        SOAPElement soapMethodElement = soapMessage.getSOAPBody().addChildElement(soapMethod, "", getSoapMethodUri());

        for (String key : parameters.keySet())
        {
            SOAPElement childElement = soapMethodElement.addChildElement(key);

            Object value = parameters.get(key);

            if (value == null)
                continue;

            if (value instanceof String)
            {
                childElement.addTextNode((String) value);
            }
            else if (value instanceof int[])
            {
                QName qName = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "int");

                for (int i : (int[])value)
                    childElement.addChildElement(qName).addTextNode(Integer.toString(i));
            }
            else if (value instanceof UUID[])
            {
                QName qName = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "guid");

                for (UUID i : (UUID[])value)
                    childElement.addChildElement(qName).addTextNode(i.toString());
            }
            else
            {
                throw new Exception("SOAPClient parameter type not implemented");
            }
        }

        soapMessage.saveChanges();

        return soapMessage;
    }

    protected SOAPMessage performSOAPCall(String soapMethod, Map<String, Object> parameters) throws Exception {

        SOAPConnection soapConnection = null;
        try
        {
            SOAPMessage requestMessage = createSOAPMessage(soapMethod, parameters);

            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();

            return soapConnection.call(requestMessage, Registry.Instance().getEmisSoapUri() + "/" + soapMethod);
        }
        finally
        {
            if (soapConnection != null)
                soapConnection.close();
        }
    }

    protected List<String> getSOAPResultAsStringArray(SOAPMessage soapResponse, String soapMethod) throws Exception {

        return getSOAPResultAsArray(soapResponse, soapMethod, "string");
    }

    protected List<UUID> getSOAPResultAsUUIDArray(SOAPMessage soapResponse, String soapMethod) throws Exception {

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

    protected List<String> getSOAPResultAsArray(SOAPMessage soapResponse, String soapMethod, String typeName) throws Exception {

        org.w3c.dom.Node soapResult = getSOAPResultAsElement(soapResponse, soapMethod);

        List<String> result = new ArrayList<>();

        for (int i = 0; i < soapResult.getChildNodes().getLength(); i++) {

            org.w3c.dom.Node node = soapResult.getChildNodes().item(i);

            if (!node.getLocalName().equals(typeName))
                throw new PrincipalSystemException("Unexpected result from principal system.");

            result.add(node.getTextContent());
        }

        return result;
    }

    protected org.w3c.dom.Node getSOAPResultAsElement(SOAPMessage soapResponse, String soapMethod) throws Exception {

        if (soapResponse.getSOAPBody().hasFault())
            throw new PrincipalSystemException("SOAP fault received from principal system.\r\n\r\n" + soapResponse.getSOAPBody().getFault().getFaultString());

        return soapResponse.getSOAPBody().getElementsByTagName(soapMethod + "Result").item(0);
    }

    protected String getSOAPResultAsString(SOAPMessage soapResponse, String soapMethod) throws Exception {

        org.w3c.dom.Node n = getSOAPResultAsElement(soapResponse, soapMethod);

        if (n != null)
            return n.getTextContent();

        return "";
    }
}
