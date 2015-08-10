package org.endeavourhealth.cim.common.repository.common.model;

/// <summary>
/// A reference to a code defined by a terminology system
/// </summary>
public class Coding {
    /// <summary>
    /// The primary code value originally used to encode a statement.
    /// </summary>
    private String code;
    /// <summary>
    /// The term associated with the code.
    /// </summary>
    private String display;
    /// <summary>
    /// Text associated with this code as selected, typed or seen by the author of this statement. This is only used when not identical to the display text.
    /// </summary>
    private String originalText;
    /// <summary>
    /// Mapping to source coding scheme (e.g. SNOMED CT concept identifier) if other coding scheme is used by the originating system.
    /// </summary>
    private CodingTranslation translation;

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

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setTranslation(CodingTranslation translation) {
        this.translation = translation;
    }

    public CodingTranslation getTranslation() {
        return translation;
    }
}