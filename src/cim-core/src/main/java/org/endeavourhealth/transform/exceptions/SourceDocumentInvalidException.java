package org.endeavourhealth.transform.exceptions;

public class SourceDocumentInvalidException extends TransformException {
    public SourceDocumentInvalidException(String message) { super(message); }
    public SourceDocumentInvalidException(String message, Throwable cause) { super(message, cause); }
    public SourceDocumentInvalidException(Throwable cause) { super(cause); }
}