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

	public Boolean validateMessage(String publicKey, String method, String body, String inboundHash) {
        // Retrieve private key based on public key
        String privateKey = getPrivateKey(publicKey);

        if (privateKey == null)
            return false;

        // Ensure private key exists first.  This allows swagger to bypass security
        // for test servers by adding a key.  Swagger should not be added to live
        // servers so cannot be used as back door.
        if ("swagger".equals(publicKey))
            return true;

        String data = method;
        if (body != null)
            data += body;

        // Hash data based on private key
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secret);
            byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            String hash = new String(Base64.encodeBase64(digest), StandardCharsets.UTF_8);
            // Compare to inbound hash
            return hash.equals(inboundHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, List<String>> getLegitimateRelationships() {
        // TODO : Implement full DP logic
        Map<String, List<String>> _legitimateRelationships = new HashMap<>();
        _legitimateRelationships.put("swagger", Arrays.asList("A99999", "B99999"));
        _legitimateRelationships.put("subsidiary", Arrays.asList("Y99999", "Z99999"));
        return _legitimateRelationships;
    }

    private String getPrivateKey(String publicKey) {
        return "privateKey";
    }
}
