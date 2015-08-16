package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMMissingParamException extends CIMException {

    public CIMMissingParamException() {
        super();
    }
    public CIMMissingParamException(String message) {
        super(message);
    }
    public CIMMissingParamException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMMissingParamException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_BAD_REQUEST;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.INVALID_REQUIRED; }
}
