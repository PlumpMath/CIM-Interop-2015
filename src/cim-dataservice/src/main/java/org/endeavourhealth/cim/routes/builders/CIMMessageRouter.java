package org.endeavourhealth.cim.routes.builders;

import org.endeavourhealth.cim.common.CIMRouteBuilder;

public class CIMMessageRouter extends CIMRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMMessageRouter")
            .routeId("CIMMessageRouter")
            .recipientList(header("MessageRouterRecipient"))
            .to("direct:CIMMessageRouterResult");
    }
}
