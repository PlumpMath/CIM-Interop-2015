package org.endeavourhealth.cim.routes.builders;

import org.endeavourhealth.cim.common.ExceptionHandlerBaseRouteBuilder;
import org.endeavourhealth.cim.processor.core.AggregationProcessor;

public class CIMDataAggregator extends ExceptionHandlerBaseRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();
        from("direct:CIMDataAggregator")
            .routeId("CIMDataAggregator")
            .process(new AggregationProcessor())
            .to("direct:CIMDataAggregatorResult");
    }
}
