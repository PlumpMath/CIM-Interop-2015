package org.endeavourhealth.cim.management.exceptionMappers;

import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BaseExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        return Response
                .status(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                .build();
    }
}
