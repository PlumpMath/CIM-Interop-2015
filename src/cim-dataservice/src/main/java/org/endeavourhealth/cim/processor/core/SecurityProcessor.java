package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.IRegistry;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.data.Repository;
import org.endeavourhealth.cim.common.data.RepositoryException;
import org.endeavourhealth.cim.exceptions.SessionException;
import org.endeavourhealth.cim.repository.apikey.data.ApiKeyRepository;
import org.endeavourhealth.cim.repository.apikey.model.ApiKey;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SecurityProcessor implements Processor {
    public static final String INVALID_SESSION = "Invalid Session";

    public void process(Exchange exchange) throws Exception {
        String publicKey = (String)exchange.getIn().getHeader(HeaderKey.ApiKey);
        String inboundHash = (String)exchange.getIn().getHeader(HeaderKey.Hash);
        String method = exchange.getFromEndpoint().getEndpointConfiguration().getParameter("path");
        String body = exchange.getIn().getBody(String.class);

        if (!validateMessage(publicKey, method, body, inboundHash)) {
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.SC_UNAUTHORIZED);
            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "text/plain");
            exchange.getIn().setBody(INVALID_SESSION);
            throw new SessionException(INVALID_SESSION);
        }
    }

    private Boolean validateMessage(String publicKey, String method, String body, String inboundHash) {

        ApiKey apikey = null;

        try {
            ApiKeyRepository apiKeyRepository = new ApiKeyRepository();
            apikey = apiKeyRepository.getById(publicKey);
        }
        catch (RepositoryException re) {
            return false;
        }

        if (apikey == null)
            return false;

        String privateKey = apikey.getSecret();

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
}
