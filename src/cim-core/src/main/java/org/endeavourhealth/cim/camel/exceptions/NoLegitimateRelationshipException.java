package org.endeavourhealth.cim.camel.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.camel.helpers.FhirIssueType;

public class NoLegitimateRelationshipException extends BaseException {

    public NoLegitimateRelationshipException() {
        super();
    }
    public NoLegitimateRelationshipException(String message) {
        super(message);
    }
    public NoLegitimateRelationshipException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoLegitimateRelationshipException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_FORBIDDEN;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.SECURITY_SUPRESSED; }
}
