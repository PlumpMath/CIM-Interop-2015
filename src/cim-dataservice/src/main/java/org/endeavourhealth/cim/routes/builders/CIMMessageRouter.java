package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;

@SuppressWarnings("unused")
public class CIMMessageRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMMessageRouter")
            .routeId("CIMMessageRouter")
            .recipientList(header("MessageRouterCallback"))
            .to("direct:CIMMessageRouterResult");
    }
}
