package org.endeavourhealth.common.core.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.common.core.FhirIssueType;

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
