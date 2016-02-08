package org.endeavourhealth.common.core.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.common.core.FhirIssueType;

public class InvalidParamException extends BaseException {

    public InvalidParamException() {
        super();
    }
    public InvalidParamException(String message) {
        super(message);
    }
    public InvalidParamException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidParamException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_BAD_REQUEST;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.INVALID_VALUE; }
}
