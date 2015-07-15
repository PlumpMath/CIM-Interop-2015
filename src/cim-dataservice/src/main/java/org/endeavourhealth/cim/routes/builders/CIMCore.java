package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;

@SuppressWarnings("unused")
public class CIMCore extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMCore")
            .routeId("CIMCore")
            .choice()
                .when(simple("${header." + HeaderKey.MessageRouterCallback + "} == null"))
                    .log(LoggingLevel.ERROR, "No destination for message route")
                    .stop()
                .otherwise()
                    .to("direct:CIMHeaderValidation");

        from("direct:CIMHeaderValidationResult")
            .routeId("CIMHeaderValidationResult")
            .wireTap("direct:CIMAudit")
                .newExchangeHeader(HeaderKey.TapLocation, constant("Inbound"))
            .end()
            .to("direct:CIMSecurity");

        from("direct:CIMSecurityResult")
            .routeId("CIMSecurityResult")
            .to("direct:CIMDataProtocol");

        from("direct:CIMDataProtocolResult")
            .routeId("CIMDataProtocolResult")
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
                .newExchangeHeader(HeaderKey.TapLocation, constant("Outbound"))
            .end()
            .to("direct:CIMDataProtocolFilter");

        from("direct:CIMDataProtocolFilterResult")
            .routeId("CIMDataProtocolFilterResult")
            .to("direct:CIMResponse");
    }
}
