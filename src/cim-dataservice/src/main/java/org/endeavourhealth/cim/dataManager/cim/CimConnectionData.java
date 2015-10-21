package org.endeavourhealth.cim.dataManager.cim;

import org.endeavourhealth.common.core.Util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CimConnectionData {
	private String _baseUri;
	private String _apiKey;
	private String _secret;

	public String getBaseUri() {
		return _baseUri;
	}

	public CimConnectionData setBaseUri(String baseUri) {
		_baseUri = baseUri;
		return this;
	}

	public String getApiKey() {
		return _apiKey;
	}

	public CimConnectionData setApiKey(String apiKey) {
		_apiKey = apiKey;
		return this;
	}

	public CimConnectionData setSecret(String secret) {
		_secret = secret;
		return this;
	}

	public String getHash(String data) throws InvalidKeyException, NoSuchAlgorithmException {
		return Util.generateHmacSha256Hash(_secret, data);
	}
}
