package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.endeavourhealth.cim.processor.core.AggregationProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMDataAggregator extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CIM_DATA_AGGREGATOR))
            .routeId(CoreRouteName.CIM_DATA_AGGREGATOR)
            .process(new AggregationProcessor())
            .to(Route.direct(CoreRouteName.CIM_DATA_AGGREGATOR_RESULT));
    }
}
