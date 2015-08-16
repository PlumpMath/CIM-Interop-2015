package org.endeavourhealth.cim.common.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.FhirIssueType;

public class CIMSecurityFailedException extends CIMException {

	public CIMSecurityFailedException() {
		super();
	}
	public CIMSecurityFailedException(String message) {
		super(message);
	}
	public CIMSecurityFailedException(String message, Throwable cause) {
		super(message, cause);
	}
	public CIMSecurityFailedException(Throwable cause) {
		super(cause);
	}

	@Override
	public int getHttpStatus() {
		return HttpStatus.SC_UNAUTHORIZED;
	}

	@Override
	public String getFhirIssueType() { return FhirIssueType.SECURITY_UNKNOWN; }
}
