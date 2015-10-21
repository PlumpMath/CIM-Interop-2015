package org.endeavourhealth.core.repository.common.model;

/// <summary>
/// A reference to a code defined by a terminology system
/// </summary>
public class CodingTranslation {
    /// <summary>
    /// Identity of the terminology system
    /// </summary>
    private String system;
    /// <summary>
    /// Version of the system - if relevant
    /// </summary>
    private String version;
    /// <summary>
    /// The primary code value originally used to encode a statement.
    /// </summary>
    private String code;
    /// <summary>
    /// Representation defined by the system
    /// </summary>
    private String display;

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystem() {
        return system;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}