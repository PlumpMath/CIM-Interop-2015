package org.endeavourhealth.core.repository.rabbit;

public class RabbitConfig {
	private String usernamePassword;
	private String uri;
	private String exchange;

	public String getUsernamePassword() {
		return usernamePassword;
	}
	public void setUsernamePassword(String usernamePassword) {
		this.usernamePassword = usernamePassword;
	}

	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getExchange() { return exchange; }
	public void setExchange(String exchange) { this.exchange = exchange; }
}
