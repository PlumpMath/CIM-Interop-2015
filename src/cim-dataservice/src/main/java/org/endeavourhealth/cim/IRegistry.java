package org.endeavourhealth.cim;

import java.util.ArrayList;

public interface IRegistry {
    String getDataAdapterTypeNameForService(String odsCode);
    ArrayList<String> getAllAdapterTypes();
    Boolean validateMessage(String publicKey, String method, String body, String inboundHash);
}
