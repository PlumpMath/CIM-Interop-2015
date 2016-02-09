package org.endeavourhealth.cim.camel.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.camel.helpers.FhirIssueType;

public class ValidationRuleException extends BaseException {

    public ValidationRuleException() {
        super();
    }
    public ValidationRuleException(String message) { super(message); }
    public ValidationRuleException(String message, Throwable cause) {
        super(message, cause);
    }
    public ValidationRuleException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() { return HttpStatus.SC_PRECONDITION_FAILED; }

    @Override
    public String getFhirIssueType() { return FhirIssueType.INVALID_INVARIANT; }
}
