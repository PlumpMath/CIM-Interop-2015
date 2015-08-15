package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.repository.common.data.RepositoryException;
import org.endeavourhealth.cim.common.exceptions.SessionException;
import org.endeavourhealth.cim.common.repository.user.data.UserRepository;
import org.endeavourhealth.cim.common.repository.user.model.User;

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

        // disable hmac checking while we add hash checking to the swagger page
        return true;

//        String data = method;
//        if (body != null)
//            data += body;
//
//        // Hash data based on private key
//        try {
//            Mac mac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secret = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//            mac.init(secret);
//            byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
//            String hash = new String(Base64.encodeBase64(digest), StandardCharsets.UTF_8);
//            // Compare to inbound hash
//            return hash.equals(inboundHash);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
    }
}
