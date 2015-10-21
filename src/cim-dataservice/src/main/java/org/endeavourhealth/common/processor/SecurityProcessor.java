package org.endeavourhealth.common.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.Util;
import org.endeavourhealth.common.core.exceptions.SecurityFailedException;
import org.endeavourhealth.core.repository.common.data.RepositoryException;
import org.endeavourhealth.core.repository.user.data.UserRepository;
import org.endeavourhealth.core.repository.user.model.User;

public class SecurityProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String apiKey = ExchangeHelper.getInHeaderString(exchange, HeaderKey.ApiKey);
        String inboundHash = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Hash);
        String method = ExchangeHelper.getEndpointPath(exchange);
        String body = ExchangeHelper.getInBodyString(exchange);

        if (!validateMessage(apiKey, method, body, inboundHash))
            throw new SecurityFailedException();
    }

    private Boolean validateMessage(String apiKey, String method, String body, String inboundHash) throws SecurityFailedException {

		if (apiKey == null || apiKey.isEmpty())
			throw new SecurityFailedException("Missing Api key");

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
