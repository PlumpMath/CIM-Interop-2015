package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.core.DataProtocolFilterProcessor;

@SuppressWarnings("unused")
public class CIMDataProtocolFilter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMDataProtocolFilter")
            .routeId("CIMDataProtocolFilter")
            .process(new DataProtocolFilterProcessor())
            .to("direct:CIMDataProtocolFilterResult");
    }
}
