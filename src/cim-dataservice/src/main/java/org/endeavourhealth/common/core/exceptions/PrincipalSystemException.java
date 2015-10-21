package org.endeavourhealth.common.core.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.common.core.FhirIssueType;

public class PrincipalSystemException extends BaseException {

    public PrincipalSystemException() {
        super();
    }
    public PrincipalSystemException(String message) {
        super(message);
    }
    public PrincipalSystemException(String message, Throwable cause) {
        super(message, cause);
    }
    public PrincipalSystemException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() { return HttpStatus.SC_BAD_GATEWAY; }

    @Override
    public String getFhirIssueType() { return FhirIssueType.TRANSIENT_EXCEPTION; }
}
