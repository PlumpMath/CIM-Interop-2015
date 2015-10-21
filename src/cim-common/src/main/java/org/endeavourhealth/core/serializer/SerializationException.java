package org.endeavourhealth.core.serializer;

public class SerializationException extends Exception {

    public SerializationException(String message, Throwable cause) { super(message, cause); }
    public SerializationException(Throwable cause) { super(cause); }
}