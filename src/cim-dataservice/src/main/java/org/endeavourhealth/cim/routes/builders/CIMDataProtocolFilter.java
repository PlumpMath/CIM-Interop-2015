package org.endeavourhealth.cim.routes.builders;

import org.endeavourhealth.cim.common.ExceptionHandlerBaseRouteBuilder;
import org.endeavourhealth.cim.processor.core.DataProtocolFilterProcessor;

@SuppressWarnings("unused")
public class CIMDataProtocolFilter extends ExceptionHandlerBaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();
        from("direct:CIMDataProtocolFilter")
            .routeId("CIMDataProtocolFilter")
            .process(new DataProtocolFilterProcessor())
            .to("direct:CIMDataProtocolFilterResult");
    }
}
