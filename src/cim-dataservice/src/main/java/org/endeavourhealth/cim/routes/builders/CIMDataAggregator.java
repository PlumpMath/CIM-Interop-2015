package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.core.AggregationProcessor;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings("unused")
public class CIMDataAggregator extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_DATA_AGGREGATOR))
            .routeId(CoreRouteName.CIM_DATA_AGGREGATOR)
            .process(new AggregationProcessor())
            .to(Route.direct(CoreRouteName.CIM_DATA_AGGREGATOR_RESULT));
    }
}
