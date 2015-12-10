package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.common.core.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.processor.demographics.TracePersonProcessor;
import org.endeavourhealth.cim.processor.demographics.TracePersonResultProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

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
