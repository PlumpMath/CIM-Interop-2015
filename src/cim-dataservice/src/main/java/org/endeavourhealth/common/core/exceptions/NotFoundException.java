package org.endeavourhealth.common.core.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.common.core.FhirIssueType;

public class NotFoundException extends BaseException {

    public NotFoundException() {
        super();
    }
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() { return HttpStatus.SC_NOT_FOUND; }

    @Override
    public String getFhirIssueType() { return FhirIssueType.PROCESSING_NOTFOUND; }
}
