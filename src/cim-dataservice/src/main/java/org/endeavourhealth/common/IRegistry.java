package org.endeavourhealth.common;

import org.endeavourhealth.cim.transform.IRecordTransformer;
import org.endeavourhealth.core.repository.informationSharingProtocols.InformationSharingProtocol;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;

public interface IRegistry {

    String getDataManagerTypeNameForService(String odsCode) throws ClassNotFoundException;
    ArrayList<String> getAllDataManagerTypes();
    String getEmisSoapUri();
    String getBaseUri(String odsCode);

	IRecordTransformer getTransformerForApiKey(String apiKey);

	InformationSharingProtocol[] getDataDistributionProtocolsForApiKey(String apiKey);
}
