package org.endeavourhealth.cim;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Registry {
    public static String getDataAdapterTypeNameForService(String odsCode) {
        return "org.endeavourhealth.cim.adapter.MockDataAdapter";
    }

    public static String getTransformerTypeNameForService(String odsCode) {
        return "org.endeavourhealth.cim.transform.openhr.OpenHRTransformer";
    }

    public static Boolean validateMessage(String publicKey, String method, String body, String inboundHash) {
        if (publicKey == "swagger")
            return true;

        // Retrieve private key based on public key
        String privateKey = getPrivateKey(publicKey);

        String data = method;
        if (body != null)
            data += body;

        // Hash data based on private key
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret = new SecretKeySpec(privateKey.getBytes(), "HmacSHA256");
            mac.init(secret);
            byte[] digest = mac.doFinal(data.getBytes());
            String hash = new String(Base64.encodeBase64(digest));
            // Compare to inbound hash
            return hash.equals(inboundHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getPrivateKey(String publicKey) {
        return "privateKey";
    }
}
