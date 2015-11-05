package org.endeavourhealth.common;

import org.endeavourhealth.cim.transform.IRecordTransformer;
import org.endeavourhealth.core.dataDistributionProtocols.DataDistributionProtocol;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.ArrayList;

public interface IRegistry {

    String getDataManagerTypeNameForService(String odsCode) throws ClassNotFoundException;
    ArrayList<String> getAllDataManagerTypes();
    String getEmisSoapUri();
    String getBaseUri(String odsCode);

	String getRabbitHost(String channelName) throws RepositoryException;
	String getRabbitLogon(String channelName) throws RepositoryException;

	IRecordTransformer getTransformerForApiKey(String apiKey);

	DataDistributionProtocol[] getDataDistributionProtocolsForApiKey(String apiKey);
}
