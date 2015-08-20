package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMValidationRuleException extends CIMException {

    public CIMValidationRuleException() {
        super();
    }
    public CIMValidationRuleException(String message) { super(message); }
    public CIMValidationRuleException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMValidationRuleException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() { return HttpStatus.SC_PRECONDITION_FAILED; }

    @Override
    public String getFhirIssueType() { return FhirIssueType.INVALID_INVARIANT; }
}
