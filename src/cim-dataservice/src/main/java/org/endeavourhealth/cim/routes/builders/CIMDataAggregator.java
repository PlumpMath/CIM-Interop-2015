package org.endeavourhealth.cim.routes.builders;

import org.endeavourhealth.cim.common.CIMRouteBuilder;
import org.endeavourhealth.cim.processor.core.AggregationProcessor;

public class CIMDataAggregator extends CIMRouteBuilder {
    @Override
    public void configure() throws Exception {
        super.configure();
        from("direct:CIMDataAggregator")
            .routeId("CIMDataAggregator")
            .process(new AggregationProcessor())
            .to("direct:CIMDataAggregatorResult");
    }
}
