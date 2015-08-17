package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.codec.binary.Base64;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.exceptions.CIMSecurityFailedException;
import org.endeavourhealth.cim.common.repository.common.data.RepositoryException;
import org.endeavourhealth.cim.common.repository.user.data.UserRepository;
import org.endeavourhealth.cim.common.repository.user.model.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SecurityProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String publicKey = ExchangeHelper.getInHeaderString(exchange, HeaderKey.ApiKey);
        String inboundHash = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Hash);
        String method = ExchangeHelper.getEndpointPath(exchange);
        String body = ExchangeHelper.getInBodyString(exchange);

        if (!validateMessage(publicKey, method, body, inboundHash))
            throw new CIMSecurityFailedException();
    }

    private Boolean validateMessage(String publicKey, String method, String body, String inboundHash) {

        User apikey = null;

        try {
            UserRepository apiKeyRepository = new UserRepository();
            apikey = apiKeyRepository.getByApiKey(publicKey);
        }
        catch (RepositoryException re) {
            return false;
        }

        if (apikey == null)
            return false;

        String privateKey = apikey.getSecret();

        if (privateKey == null)
            return false;

		// Ensure method always has trailing "/" - required to guarantee matching hash with Crypto-JS
		if (method.endsWith("/") == false)
			method += "/";

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
}
