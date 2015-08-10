package org.endeavourhealth.cim.common.repository.common.model;

public class Extension {
    /// <summary>
    /// identifies the meaning of the extension
    /// </summary>
    private String key;
    /// <summary>
    /// Value of extension
    /// </summary>
    private String value;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
