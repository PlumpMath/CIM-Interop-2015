package org.endeavourhealth.cim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IRegistry {
    String getDataAdapterTypeNameForService(String odsCode);
    ArrayList<String> getAllAdapterTypes();
    Boolean validateMessage(String publicKey, String method, String body, String inboundHash);
    Map<String,List<String>> getLegitimateRelationships();
}
