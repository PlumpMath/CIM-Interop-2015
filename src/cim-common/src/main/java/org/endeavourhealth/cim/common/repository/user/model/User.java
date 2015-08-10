package org.endeavourhealth.cim.common.repository.user.model;

public class User {
    private String email;
    private String apiKey;
    private String secret;
    private String data;
    private String dataSchemaVersion;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataSchemaVersion() {
        return dataSchemaVersion;
    }

    public void setDataSchemaVersion(String dataSchemaVersion) {
        this.dataSchemaVersion = dataSchemaVersion;
    }
}
