package org.endeavourhealth.common.core.exceptions;

import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.common.core.FhirIssueType;

public class SecurityFailedException extends BaseException {

	public SecurityFailedException() {
		super();
	}
	public SecurityFailedException(String message) {
		super(message);
	}
	public SecurityFailedException(String message, Throwable cause) {
		super(message, cause);
	}
	public SecurityFailedException(Throwable cause) {
		super(cause);
	}

	@Override
	public int getHttpStatus() {
		return HttpStatus.SC_UNAUTHORIZED;
	}

	@Override
	public String getFhirIssueType() { return FhirIssueType.SECURITY_UNKNOWN; }
}
