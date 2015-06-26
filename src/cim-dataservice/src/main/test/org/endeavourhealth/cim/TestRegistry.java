package org.endeavourhealth.cim;

import java.util.ArrayList;

public class TestRegistry implements IRegistry {
    public String getDataAdapterTypeNameForService(String odsCode) {
        return "org.endeavourhealth.cim.adapter.TestDataAdapter";
    }

	public ArrayList<String> getAllAdapterTypes() {
        ArrayList<String> adapterTypes = new ArrayList<>();

        adapterTypes.add("org.endeavourhealth.cim.adapter.TestDataAdapter");
        adapterTypes.add("org.endeavourhealth.cim.adapter.TestDataAdapter");
        adapterTypes.add("org.endeavourhealth.cim.adapter.TestDataAdapter");

        return adapterTypes;
    }

	public Boolean validateMessage(String publicKey, String method, String body, String inboundHash) {
        return true;
    }

    private String getPrivateKey(String publicKey) {
        return publicKey;
    }
}
