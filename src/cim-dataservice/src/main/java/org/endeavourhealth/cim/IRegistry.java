package org.endeavourhealth.cim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IRegistry {
    String getDataAdapterTypeNameForService(String odsCode);
    ArrayList<String> getAllAdapterTypes();
    Map<String,List<String>> getLegitimateRelationships();
    String getPrivateKey(String publicKey);
}
