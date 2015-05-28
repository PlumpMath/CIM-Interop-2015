package org.endeavourhealth.cim.routes.builders;

import org.endeavourhealth.cim.common.CIMRouteBuilder;

public class CIMCore extends CIMRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMCore")
            .routeId("CIMCore")
            .to("direct:CIMHeaderValidation");

        from("direct:CIMHeaderValidationResult")
            .routeId("CIMHeaderValidationResult")
            .wireTap("direct:CIMAudit")
                .newExchangeHeader("TapLocation", constant("Inbound"))
            .end()
            .to("direct:CIMSecurity");

        from("direct:CIMSecurityResult")
            .routeId("CIMSecurityResult")
            .to("direct:CIMPayloadValidation");

        from("direct:CIMPayloadValidationResult")
            .routeId("CIMPayloadValidationResult")
            .to("direct:CIMMessageRouter");

        from("direct:CIMMessageRouterResult")
            .routeId("CIMMessageRouterResult")
            .to("direct:CIMDataAggregator");

        from("direct:CIMDataAggregatorResult")
            .routeId("CIMDataAggregatorResult")
            .wireTap("direct:CIMAudit")
                .newExchangeHeader("TapLocation", constant("Outbound"))
            .end()
            .to("direct:CIMResponse");
    }
}
