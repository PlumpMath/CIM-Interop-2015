package org.endeavourhealth.common.core.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.common.core.FhirIssueType;

public class InvalidInternalIdentifier extends BaseException {

    public InvalidInternalIdentifier() {
        super();
    }
    public InvalidInternalIdentifier(String message) {
        super(message);
    }
    public InvalidInternalIdentifier(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidInternalIdentifier(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_BAD_REQUEST;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.INVALID_VALUE; }
}
