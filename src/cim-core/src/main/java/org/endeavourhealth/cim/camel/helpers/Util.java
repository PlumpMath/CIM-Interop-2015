package org.endeavourhealth.cim.camel.helpers;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Util {
	public static String generateHmacSha256Hash(String secret, String data) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		mac.init(keySpec);
		byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
		String hash = new String(Base64.encodeBase64(digest), StandardCharsets.UTF_8);
		return hash;
	}
}
