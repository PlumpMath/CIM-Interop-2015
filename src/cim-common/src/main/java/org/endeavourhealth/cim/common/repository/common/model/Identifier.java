package org.endeavourhealth.cim.common.repository.common.model;

/// <summary>
/// An identifier intended for computation
/// </summary>
public class Identifier {
    /// <summary>
    /// Identifies the purpose for this identifier, if known
    /// </summary>
    public enum IdentifierUse
    {
        Usual, // the identifier recommended for display and use in real-world interactions.
        Official, // the identifier considered to be most trusted for the identification of this item.
        Temp, // A temporary identifier.
        Secondary // An identifier that was assigned in secondary use - it serves to identify the object in a relative context, but cannot be consistently assigned to the same object again in a different context.
    }

    /// <summary>
    /// usual | official | temp | secondary (If known)
    /// </summary>
    private IdentifierUse use;
    /// <summary>
    /// Description of identifier
    /// </summary>
    private String label;
    /// <summary>
    /// The namespace for the identifier
    /// </summary>
    private String system;
    /// <summary>
    /// The value that is unique
    /// </summary>
    private String value;
    /// <summary>
    /// Time period when id is/was valid for use
    /// </summary>
    private Period period;

    public void setUse(IdentifierUse use) {
        this.use = use;
    }

    public IdentifierUse getUse() {
        return use;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystem() {
        return system;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Period getPeriod() {
        return period;
    }
}