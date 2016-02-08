package org.endeavourhealth.common.core.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.common.core.FhirIssueType;

public class MissingParamException extends BaseException {

    public MissingParamException() {
        super();
    }
    public MissingParamException(String message) {
        super(message);
    }
    public MissingParamException(String message, Throwable cause) {
        super(message, cause);
    }
    public MissingParamException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_BAD_REQUEST;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.INVALID_REQUIRED; }
}
