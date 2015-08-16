package org.endeavourhealth.cim.common.exceptions;

import org.endeavourhealth.cim.common.FhirIssueType;
import org.hl7.fhir.instance.model.CodeableConcept;

public abstract class CIMException extends Exception {

    public CIMException() {
        super();
    }
    public CIMException(String message) { super(message); }
    public CIMException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMException(Throwable cause) {
        super(cause);
    }

    public abstract int getHttpStatus();
    public abstract String getFhirIssueType();

    public CodeableConcept getFhirCodeableConcept() {
        return FhirIssueType.getCodeableConcept(getFhirIssueType());
    }
}
