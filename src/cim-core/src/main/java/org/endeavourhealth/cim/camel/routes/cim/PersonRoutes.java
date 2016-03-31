package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.helpers.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.processors.demographics.TracePersonProcessor;
import org.endeavourhealth.cim.camel.processors.demographics.TracePersonResultProcessor;
import org.endeavourhealth.cim.camel.routes.common.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PersonRoutes extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String TRACE_PERSON_ROUTE = "TracePerson";

        final String BASE_PATH = "/Person/$trace";
        final String TRACE_URI = "?identifier={nhsNumber}&name={name}&dob={dob}&gender={gender}";

        // endpoints
        rest(BASE_PATH)

        .get(TRACE_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + TRACE_URI)
            .to(BaseRouteBuilder.direct(TRACE_PERSON_ROUTE))
        .endRest();

        // routes
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
