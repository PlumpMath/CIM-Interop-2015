package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMConnectionException extends CIMException {

    public CIMConnectionException() {
        super();
    }
    public CIMConnectionException(String message) {
        super(message);
    }
    public CIMConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMConnectionException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_GATEWAY_TIMEOUT;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.TRANSIENT_TIMEOUT; }
}
