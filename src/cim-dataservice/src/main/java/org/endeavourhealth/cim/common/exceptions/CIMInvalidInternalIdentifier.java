package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMInvalidInternalIdentifier extends CIMException {

    public CIMInvalidInternalIdentifier() {
        super();
    }
    public CIMInvalidInternalIdentifier(String message) {
        super(message);
    }
    public CIMInvalidInternalIdentifier(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMInvalidInternalIdentifier(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_BAD_REQUEST;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.INVALID_VALUE; }
}
