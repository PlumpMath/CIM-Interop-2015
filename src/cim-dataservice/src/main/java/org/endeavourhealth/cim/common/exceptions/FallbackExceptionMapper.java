package org.endeavourhealth.cim.common.exceptions;

import javax.xml.soap.SOAPException;

public class FallbackExceptionMapper {

    public static CIMException getCIMException(Exception exception) {

        // do not re-map CIMExceptions
        if (CIMException.class.isAssignableFrom(exception.getClass()))
            return (CIMException)exception;

        // cases to map
        if (exception instanceof SOAPException)
            return new CIMConnectionException(exception);

        // fall back case
        return new CIMUnexpectedException(exception);
    }
}
