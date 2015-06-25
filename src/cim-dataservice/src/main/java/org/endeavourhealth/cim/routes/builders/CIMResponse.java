package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.core.ResponseProcessor;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CIMResponse extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMResponse")
                .routeId("CIMResponse")
                .process(new ResponseProcessor());
    }
}
