package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;

@SuppressWarnings("unused")
public class CIMMessageRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMMessageRouter")
            .routeId("CIMMessageRouter")
            .recipientList(header(HeaderKey.MessageRouterCallback))
            .to("direct:CIMMessageRouterResult");
    }
}
