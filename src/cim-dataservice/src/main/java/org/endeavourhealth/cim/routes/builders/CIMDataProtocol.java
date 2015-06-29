package org.endeavourhealth.cim.routes.builders;

import org.endeavourhealth.cim.common.ExceptionHandlerBaseRouteBuilder;
import org.endeavourhealth.cim.processor.core.DataProtocolProcessor;

@SuppressWarnings("unused")
public class CIMDataProtocol extends ExceptionHandlerBaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();

        from("direct:CIMDataProtocol")
            .routeId("CIMDataProtocol")
            .process(new DataProtocolProcessor())
            .to("direct:CIMDataProtocolResult");
    }
}
