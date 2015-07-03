package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.core.AggregationProcessor;

@SuppressWarnings("unused")
public class CIMDataAggregator extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:CIMDataAggregator")
            .routeId("CIMDataAggregator")
            .process(new AggregationProcessor())
            .to("direct:CIMDataAggregatorResult");
    }
}
