package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.processor.AggregationProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class DataAggregator extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.DATA_AGGREGATOR))
            .routeId(ComponentRouteName.DATA_AGGREGATOR)
            .process(new AggregationProcessor());
    }
}
