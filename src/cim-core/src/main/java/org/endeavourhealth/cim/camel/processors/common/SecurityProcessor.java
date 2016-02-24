package org.endeavourhealth.cim.camel.processors.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.helpers.PropertyKey;
import org.endeavourhealth.cim.camel.helpers.Util;
import org.endeavourhealth.cim.camel.exceptions.SecurityFailedException;
import org.endeavourhealth.cim.repository.framework.RepositoryException;
import org.endeavourhealth.cim.repository.domains.user.UserRepository;
import org.endeavourhealth.cim.repository.domains.user.User;

import java.util.UUID;

public class SecurityProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        exchange.setProperty(PropertyKey.AuditId, UUID.randomUUID());

        String apiKey = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.ApiKey);
        String inboundHash = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Hash);
        String method = ExchangeHelper.getEndpointPath(exchange);
        String body = ExchangeHelper.getInBodyString(exchange);

        User user = validateMessage(apiKey, method, body, inboundHash);

        exchange.setProperty(PropertyKey.Email, user.getEmail());
    }

    private User validateMessage(String apiKey, String method, String body, String inboundHash) throws SecurityFailedException {

		if (apiKey == null || apiKey.isEmpty())
			throw new SecurityFailedException("Missing Api key");

        User user = null;

        try {
            user = UserRepository.getInstance().getByApiKey(apiKey);
        }
        catch (RepositoryException re) {
            throw new SecurityFailedException("User repository error");
        }

        if (user == null)
			throw new SecurityFailedException("Invalid user");

        String userSecret = user.getSecret();

        if (userSecret == null)
			throw new SecurityFailedException("User has no secret");

        String data = method;
        if (body != null)
            data += body;

        // Hash data based on private key
		String hash;
        try {
            hash = Util.generateHmacSha256Hash(userSecret, data);
        } catch (Exception e) {
            e.printStackTrace();
			throw new SecurityFailedException("Hash calculation error");
        }

		// Compare to inbound hash
		if (hash.equals(inboundHash) == false)
			throw new SecurityFailedException("Incorrect hash");

        return user;
    }
}
