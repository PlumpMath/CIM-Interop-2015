package org.endeavourhealth.cim.common;

import org.apache.camel.builder.RouteBuilder;

public abstract class CIMRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .to("direct:CIMInvalidMessage");
    }
}
