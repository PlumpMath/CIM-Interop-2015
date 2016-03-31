package org.endeavourhealth.cim.dataManager;

import java.util.ArrayList;

public interface IRegistry {

    String getDataManagerTypeNameForService(String odsCode) throws ClassNotFoundException;
    ArrayList<String> getAllDataManagerTypes();
    String getEmisSoapUri();
    String getBaseUri(String odsCode);
}
