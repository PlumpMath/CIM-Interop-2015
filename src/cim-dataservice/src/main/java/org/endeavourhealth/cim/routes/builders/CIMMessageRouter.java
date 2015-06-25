package org.endeavourhealth.cim.routes.builders;

import org.endeavourhealth.cim.common.ExceptionHandlerBaseRouteBuilder;

@SuppressWarnings("unused")
public class CIMMessageRouter extends ExceptionHandlerBaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMMessageRouter")
            .routeId("CIMMessageRouter")
            .recipientList(header("MessageRouterCallback"))
            .to("direct:CIMMessageRouterResult");
    }
}
