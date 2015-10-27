package org.endeavourhealth.cim;

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
	public String getRabbitHost() {
		return null;
	}

	public String getPrivateKey(String publicKey) {
        if ("swagger".equals(publicKey))
            return "privateKey";

        if ("null".equals(publicKey))
            return null;

        return publicKey;
    }
}
