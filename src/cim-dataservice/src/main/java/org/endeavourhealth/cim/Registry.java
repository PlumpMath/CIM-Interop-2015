package org.endeavourhealth.cim;

import java.util.*;

public class Registry implements IRegistry {
    private static IRegistry _instance;
	public static IRegistry Instance() {
		if (_instance == null)
			_instance = new Registry();

		return _instance;
	}

	public static void setInstance(IRegistry registry) {
		_instance = registry;
	}

    public String getDataManagerTypeNameForService(String odsCode) throws ClassNotFoundException {
        return "org.endeavourhealth.cim.dataManager.EmisDataManager";
    }

	public ArrayList<String> getAllDataManagerTypes() {
        ArrayList<String> dataManagerTypes = new ArrayList<>();

        dataManagerTypes.add("org.endeavourhealth.cim.dataManager.EmisDataManager");
        dataManagerTypes.add("org.endeavourhealth.cim.dataManager.EmisDataManager");
        dataManagerTypes.add("org.endeavourhealth.cim.dataManager.EmisDataManager");

        return dataManagerTypes;
    }

    public Map<String, List<String>> getLegitimateRelationships() {
        // TODO : Implement full DP logic
        Map<String, List<String>> _legitimateRelationships = new HashMap<>();
        _legitimateRelationships.put("swagger", Arrays.asList("A99999", "B99999"));
        _legitimateRelationships.put("subsidiary", Arrays.asList("Y99999", "Z99999"));
        return _legitimateRelationships;
    }

    public String getPrivateKey(String publicKey) {
        return "privateKey";
    }
}
