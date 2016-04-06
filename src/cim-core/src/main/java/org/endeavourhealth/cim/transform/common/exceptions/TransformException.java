package org.endeavourhealth.cim.transform.common.exceptions;

public class TransformException extends Exception {
    public TransformException(String message) { super(message); }
    public TransformException(String message, Throwable cause) { super(message, cause); }
    public TransformException(Throwable cause) { super(cause); }
}