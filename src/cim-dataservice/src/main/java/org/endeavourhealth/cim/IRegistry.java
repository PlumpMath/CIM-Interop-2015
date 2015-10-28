package org.endeavourhealth.cim;

import org.endeavourhealth.cim.transform.IRecordTransformer;
import org.endeavourhealth.core.dataDistributionProtocols.DataDistributionProtocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IRegistry {

    String getDataManagerTypeNameForService(String odsCode) throws ClassNotFoundException;
    ArrayList<String> getAllDataManagerTypes();
    String getEmisSoapUri();
    String getBaseUri(String odsCode);
	String getRabbitHost();

	String getRabbitLogon();

	IRecordTransformer getTransformerForApiKey(String apiKey);

	DataDistributionProtocol[] getDataDistributionProtocolsForApiKey(String apiKey);
}
