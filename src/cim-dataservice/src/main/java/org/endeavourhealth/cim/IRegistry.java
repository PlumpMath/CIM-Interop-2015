package org.endeavourhealth.cim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IRegistry {
    String getDataManagerTypeNameForService(String odsCode) throws ClassNotFoundException;
    ArrayList<String> getAllDataManagerTypes();
    Map<String,List<String>> getLegitimateRelationships();
    String getPrivateKey(String publicKey);
}
