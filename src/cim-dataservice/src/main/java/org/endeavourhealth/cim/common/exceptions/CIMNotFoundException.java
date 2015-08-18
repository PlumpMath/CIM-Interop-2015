package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMNotFoundException extends CIMException {

    public CIMNotFoundException() {
        super();
    }
    public CIMNotFoundException(String message) {
        super(message);
    }
    public CIMNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMNotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() { return HttpStatus.SC_NOT_FOUND; }

    @Override
    public String getFhirIssueType() { return FhirIssueType.PROCESSING_NOTFOUND; }
}
