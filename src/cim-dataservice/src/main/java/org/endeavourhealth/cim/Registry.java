package org.endeavourhealth.cim;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
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

    public String getDataAdapterTypeNameForService(String odsCode) {
        return "org.endeavourhealth.cim.adapter.MockDataAdapter";
    }

	public ArrayList<String> getAllAdapterTypes() {
        ArrayList<String> adapterTypes = new ArrayList<>();

        adapterTypes.add("org.endeavourhealth.cim.adapter.MockDataAdapter");
        adapterTypes.add("org.endeavourhealth.cim.adapter.MockDataAdapter");
        adapterTypes.add("org.endeavourhealth.cim.adapter.MockDataAdapter");

        return adapterTypes;
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
