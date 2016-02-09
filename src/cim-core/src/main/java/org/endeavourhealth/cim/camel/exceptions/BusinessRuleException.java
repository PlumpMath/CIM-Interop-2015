package org.endeavourhealth.cim.camel.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.camel.helpers.FhirIssueType;

public class BusinessRuleException extends BaseException {

    public BusinessRuleException() {
        super();
    }
    public BusinessRuleException(String message) {
        super(message);
    }
    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }
    public BusinessRuleException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() { return HttpStatus.SC_PRECONDITION_FAILED; }

    @Override
    public String getFhirIssueType() { return FhirIssueType.PROCESSING_BUSINESSRULES; }
}
