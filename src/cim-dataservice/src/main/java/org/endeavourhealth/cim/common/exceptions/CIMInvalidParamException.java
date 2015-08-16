package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMInvalidParamException extends CIMException {

    public CIMInvalidParamException() {
        super();
    }
    public CIMInvalidParamException(String message) {
        super(message);
    }
    public CIMInvalidParamException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMInvalidParamException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_BAD_REQUEST;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.INVALID_VALUE; }
}
