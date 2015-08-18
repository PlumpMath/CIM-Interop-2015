package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMBusinessRuleConflictException extends CIMException {

    public CIMBusinessRuleConflictException() {
        super();
    }
    public CIMBusinessRuleConflictException(String message) { super(message); }
    public CIMBusinessRuleConflictException(String message, Throwable cause) { super(message, cause); }
    public CIMBusinessRuleConflictException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() { return HttpStatus.SC_CONFLICT; }

    @Override
    public String getFhirIssueType() { return FhirIssueType.PROCESSING_CONFLICT; }
}
