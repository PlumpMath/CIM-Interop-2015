package org.endeavourhealth.cim.dataManager;

import org.endeavourhealth.cim.transform.IRecordTransformer;

import java.util.ArrayList;

public interface IRegistry {

    String getDataManagerTypeNameForService(String odsCode) throws ClassNotFoundException;
    ArrayList<String> getAllDataManagerTypes();
    String getEmisSoapUri();
    String getBaseUri(String odsCode);

	IRecordTransformer getTransformerForContentType(String contentType);
}
