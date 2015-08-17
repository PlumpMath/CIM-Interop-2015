package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMPrincipalSystemException extends CIMException {

    public CIMPrincipalSystemException() {
        super();
    }
    public CIMPrincipalSystemException(String message) {
        super(message);
    }
    public CIMPrincipalSystemException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMPrincipalSystemException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() { return HttpStatus.SC_BAD_GATEWAY; }

    @Override
    public String getFhirIssueType() { return FhirIssueType.TRANSIENT_EXCEPTION; }
}
