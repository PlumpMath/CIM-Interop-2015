package org.endeavourhealth.cim.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.camel.helpers.FhirIssueType;

// don't throw this.  used to hold default values for non-BaseExceptions.
public class UnexpectedException extends BaseException {

    public UnexpectedException() {
        super();
    }
    public UnexpectedException(String message) {
        super(message);
    }
    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }
    public UnexpectedException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.TRANSIENT_EXCEPTION; }
}
