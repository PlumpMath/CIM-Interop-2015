package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CIMInvalidMessage extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:CIMInvalidMessage")
            .routeId("CIMInvalidMessage")
            .log(LoggingLevel.ERROR, "InvalidMessage")
            .to("direct:CIMResponse")
            .stop();
    }
}
