package org.endeavourhealth.common.core.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.common.core.FhirIssueType;

public class ConnectionException extends BaseException {

    public ConnectionException() {
        super();
    }
    public ConnectionException(String message) {
        super(message);
    }
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
    public ConnectionException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_GATEWAY_TIMEOUT;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.TRANSIENT_TIMEOUT; }
}
