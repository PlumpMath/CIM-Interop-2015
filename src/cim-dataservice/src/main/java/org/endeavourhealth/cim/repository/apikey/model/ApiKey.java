package org.endeavourhealth.cim.repository.apikey.model;

public class ApiKey {
    private String apiKey;
    private String secret;
    private String userdata;
    private String userDataSchemaVersion;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUserdata() {
        return userdata;
    }

    public void setUserdata(String userdata) {
        this.userdata = userdata;
    }

    public String getUserDataSchemaVersion() {
        return userDataSchemaVersion;
    }

    public void setUserDataSchemaVersion(String userDataSchemaVersion) {
        this.userDataSchemaVersion = userDataSchemaVersion;
    }
}
