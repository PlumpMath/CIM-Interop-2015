package org.endeavourhealth.cim.common;

import org.apache.camel.builder.RouteBuilder;

public abstract class ExceptionHandlerBaseRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .to("direct:CIMInvalidMessage");
    }
}
