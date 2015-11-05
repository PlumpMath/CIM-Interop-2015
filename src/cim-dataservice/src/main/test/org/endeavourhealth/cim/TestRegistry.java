package org.endeavourhealth.cim;

import org.endeavourhealth.cim.transform.IRecordTransformer;
import org.endeavourhealth.common.IRegistry;
import org.endeavourhealth.core.dataDistributionProtocols.DataDistributionProtocol;

import java.util.*;

public class TestRegistry implements IRegistry {
    public String getDataManagerTypeNameForService(String odsCode) {
        return "org.endeavourhealth.cim.dataManager.EmisTestDataManager";
    }

	public ArrayList<String> getAllDataManagerTypes() {
        ArrayList<String> adapterTypes = new ArrayList<>();

        adapterTypes.add("org.endeavourhealth.cim.dataManager.TestEmisDataManager");
        adapterTypes.add("org.endeavourhealth.cim.dataManager.TestEmisDataManager");
        adapterTypes.add("org.endeavourhealth.cim.dataManager.TestEmisDataManager");

        return adapterTypes;
    }

    @Override
    public String getEmisSoapUri() {
        return null;
    }

    @Override
    public String getBaseUri(String odsCode) {
        return null;
    }

	@Override
	public String getRabbitHost(String channelName) {
		return null;
	}

	@Override
	public String getRabbitLogon(String channelName) {
		return null;
	}

	@Override
	public IRecordTransformer getTransformerForApiKey(String apiKey) {
		return null;
	}

	@Override
	public DataDistributionProtocol[] getDataDistributionProtocolsForApiKey(String apiKey) {
		return new DataDistributionProtocol[0];
	}

	public String getPrivateKey(String publicKey) {
        if ("swagger".equals(publicKey))
            return "privateKey";

        if ("null".equals(publicKey))
            return null;

        return publicKey;
    }
}
