package org.endeavourhealth.common.routes.core;

import org.endeavourhealth.common.processor.AggregationProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings("unused")
public class DataAggregator extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.DATA_AGGREGATOR))
            .routeId(CoreRouteName.DATA_AGGREGATOR)
            .process(new AggregationProcessor());
    }
}
