package org.endeavourhealth.core.repository.rabbit;

public class RabbitConfig {
	private String channelName;
	private String usernamePassword;
	private String uri;
	private String dataSchemaVersion;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

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

	public String getDataSchemaVersion() {
		return dataSchemaVersion;
	}

	public void setDataSchemaVersion(String dataSchemaVersion) {
		this.dataSchemaVersion = dataSchemaVersion;
	}
}
