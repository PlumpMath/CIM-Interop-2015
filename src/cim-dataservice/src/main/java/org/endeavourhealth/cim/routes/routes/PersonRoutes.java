package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.common.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.processor.demographics.TracePersonProcessor;
import org.endeavourhealth.cim.processor.demographics.TracePersonResultProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PersonRoutes extends CIMRouteBuilder {
	public static final String TRACE_PERSON_ROUTE = "TracePerson";

    @Override
    public void configureRoute() throws Exception {

        buildCimCallbackRoute(TRACE_PERSON_ROUTE)
            .setBody(constant(DataManagerFactory.getAllDataAdapters()))
            .setHeader(HeaderKey.AdapterCount, simple("${body.size}"))
            .split(body(), new ArrayListAggregationStrategy())
                .parallelProcessing()
                .process(new TracePersonProcessor())
            .end()
            .process(new TracePersonResultProcessor());
    }
}
