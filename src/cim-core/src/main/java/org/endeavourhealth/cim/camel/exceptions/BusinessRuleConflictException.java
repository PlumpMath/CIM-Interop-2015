package org.endeavourhealth.cim.camel.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.camel.helpers.FhirIssueType;

public class BusinessRuleConflictException extends BaseException {

    public BusinessRuleConflictException() {
        super();
    }
    public BusinessRuleConflictException(String message) { super(message); }
    public BusinessRuleConflictException(String message, Throwable cause) { super(message, cause); }
    public BusinessRuleConflictException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() { return HttpStatus.SC_CONFLICT; }

    @Override
    public String getFhirIssueType() { return FhirIssueType.PROCESSING_CONFLICT; }
}
