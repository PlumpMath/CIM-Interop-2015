package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.Util;
import org.endeavourhealth.cim.common.exceptions.CIMSecurityFailedException;
import org.endeavourhealth.cim.common.repository.common.data.RepositoryException;
import org.endeavourhealth.cim.common.repository.user.data.UserRepository;
import org.endeavourhealth.cim.common.repository.user.model.User;

public class SecurityProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String apiKey = ExchangeHelper.getInHeaderString(exchange, HeaderKey.ApiKey);
        String inboundHash = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Hash);
        String method = ExchangeHelper.getEndpointPath(exchange);
        String body = ExchangeHelper.getInBodyString(exchange);

        if (!validateMessage(apiKey, method, body, inboundHash))
            throw new CIMSecurityFailedException();
    }

    private Boolean validateMessage(String apiKey, String method, String body, String inboundHash) throws CIMSecurityFailedException {

		if (apiKey == null || apiKey.isEmpty())
			throw new CIMSecurityFailedException("Missing Api key");

        User user = null;

        try {
            UserRepository apiKeyRepository = new UserRepository();
            user = apiKeyRepository.getByApiKey(apiKey);
        }
        catch (RepositoryException re) {
            return false;
        }

        if (user == null)
            return false;

        String userSecret = user.getSecret();

        if (userSecret == null)
            return false;

        String data = method;
        if (body != null)
            data += body;

        // Hash data based on private key
        try {
            String hash = Util.generateHmacSha256Hash(userSecret, data);
			// Compare to inbound hash
            return hash.equals(inboundHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
