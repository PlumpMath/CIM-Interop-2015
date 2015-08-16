package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

// don't throw this.  used to hold default values for non-CIMExceptions.
public class CIMUnexpectedException extends CIMException {

    public CIMUnexpectedException() {
        super();
    }
    public CIMUnexpectedException(String message) {
        super(message);
    }
    public CIMUnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMUnexpectedException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.TRANSIENT_EXCEPTION; }
}
