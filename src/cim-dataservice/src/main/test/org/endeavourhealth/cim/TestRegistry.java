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
    public Map<String, List<String>> getLegitimateRelationships() {
        // TODO : Implement full DP logic
        Map<String, List<String>> _legitimateRelationships = new HashMap<>();
        _legitimateRelationships.put("swagger", Arrays.asList("A99999", "B99999"));
        _legitimateRelationships.put("subsidiary", Arrays.asList("Y99999", "Z99999"));
        return _legitimateRelationships;
    }

    public String getPrivateKey(String publicKey) {
        if ("swagger".equals(publicKey))
            return "privateKey";

        if ("null".equals(publicKey))
            return null;

        return publicKey;
    }
}
