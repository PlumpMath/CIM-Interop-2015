package org.endeavourhealth.cim.dataprotocols.admin.models;

public class    Identifier {

    public enum IdentifierType {
        ODSCode
    }

    private IdentifierType type;
    private String value;

    public IdentifierType getType() {
        return type;
    }

    public void setType(IdentifierType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
