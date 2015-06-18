package org.endeavourhealth.cim.routes.builders;

import org.endeavourhealth.cim.common.ExceptionHandlerBaseRouteBuilder;
import org.endeavourhealth.cim.processor.core.DataProtocolsProcessor;

public class CIMDataProtocols extends ExceptionHandlerBaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();
        from("direct:CIMDataProtocols")
            .routeId("CIMDataProtocols")
            .process(new DataProtocolsProcessor())
            .to("direct:CIMDataProtocolsResult");
    }
}
