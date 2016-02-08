package org.endeavourhealth.common.core.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.common.core.FhirIssueType;

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
