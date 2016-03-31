package org.endeavourhealth.cim.transform.common.exceptions;

public class TransformFeatureNotSupportedException extends TransformException {
    public TransformFeatureNotSupportedException(String message) { super(message); }
    public TransformFeatureNotSupportedException(String message, Throwable cause) { super(message, cause); }
    public TransformFeatureNotSupportedException(Throwable cause) { super(cause); }
}