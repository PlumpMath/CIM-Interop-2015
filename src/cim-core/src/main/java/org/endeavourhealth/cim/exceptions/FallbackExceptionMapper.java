package org.endeavourhealth.cim.exceptions;

import javax.xml.soap.SOAPException;

public class FallbackExceptionMapper {

    public static BaseException getException(Exception exception) {

        // do not re-map BaseExceptions
        if (BaseException.class.isAssignableFrom(exception.getClass()))
            return (BaseException)exception;

        // cases to map
        if (exception instanceof SOAPException)
            return new ConnectionException(exception);

        // fall back case
        return new UnexpectedException(exception);
    }
}
