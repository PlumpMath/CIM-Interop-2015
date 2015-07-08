package org.endeavourhealth.cim.common.configuration.nodes;

public class InternalProcessingException extends Exception {
    public InternalProcessingException(String message) { super(message); }
    public InternalProcessingException(String message, Throwable cause) { super(message, cause); }
    public InternalProcessingException(Throwable cause) { super(cause); }
}