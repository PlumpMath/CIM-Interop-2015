package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.camel.processors.demographics.TracePersonProcessor;
import org.endeavourhealth.cim.camel.helpers.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.camel.processors.demographics.TracePersonResultProcessor;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PersonRoutes extends BaseRouteBuilder {
	public static final String TRACE_PERSON_ROUTE = "TracePerson";

    @Override
    public void configureRoute() throws Exception {

        buildWrappedRoute(CimCore.ROUTE_NAME, TRACE_PERSON_ROUTE)
            .setBody(constant(DataManagerFactory.getAllDataAdapters()))
            .setHeader(CIMHeaderKey.AdapterCount, simple("${body.size}"))
            .split(body(), new ArrayListAggregationStrategy())
                .parallelProcessing()
                .process(new TracePersonProcessor())
            .end()
            .process(new TracePersonResultProcessor());
    }
}
