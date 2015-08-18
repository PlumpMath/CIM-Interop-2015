package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMBusinessRuleException extends CIMException {

    public CIMBusinessRuleException() {
        super();
    }
    public CIMBusinessRuleException(String message) {
        super(message);
    }
    public CIMBusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMBusinessRuleException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() { return HttpStatus.SC_PRECONDITION_FAILED; }

    @Override
    public String getFhirIssueType() { return FhirIssueType.PROCESSING_BUSINESSRULES; }
}
