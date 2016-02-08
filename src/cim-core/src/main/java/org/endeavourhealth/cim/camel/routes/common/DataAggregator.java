package org.endeavourhealth.cim.camel.routes.common;

import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.processors.common.AggregationProcessor;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;

@SuppressWarnings("unused")
public class DataAggregator extends BaseRouteBuilder
{

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.DATA_AGGREGATOR))
            .routeId(ComponentRouteName.DATA_AGGREGATOR)
            .process(new AggregationProcessor());
    }
}
