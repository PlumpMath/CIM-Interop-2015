package org.endeavourhealth.cim.common.serializer;

public class SerializationException extends Exception {

    public SerializationException(String message, Throwable cause) { super(message, cause); }
    public SerializationException(Throwable cause) { super(cause); }
}