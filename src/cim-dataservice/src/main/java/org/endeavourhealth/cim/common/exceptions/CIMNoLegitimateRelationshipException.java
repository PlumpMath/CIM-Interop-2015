package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMNoLegitimateRelationshipException extends CIMException {

    public CIMNoLegitimateRelationshipException() {
        super();
    }
    public CIMNoLegitimateRelationshipException(String message) {
        super(message);
    }
    public CIMNoLegitimateRelationshipException(String message, Throwable cause) {
        super(message, cause);
    }
    public CIMNoLegitimateRelationshipException(Throwable cause) {
        super(cause);
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.SC_FORBIDDEN;
    }

    @Override
    public String getFhirIssueType() { return FhirIssueType.SECURITY_SUPRESSED; }
}
