package org.endeavourhealth.cim.routes.routeBuilders.endpoints;

import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.common.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.processor.demographics.*;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PersonEndpoint extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String BASE_ROUTE = "/Person";
        final String TRACE_ROUTE = "?identifier={nhsNumber}&name={name}&dob={dob}&gender={gender}";

        final String TRACE_PROCESSOR_ROUTE = "TracePerson";

        rest(BASE_ROUTE)

        .get(TRACE_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + TRACE_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(TRACE_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(TRACE_PROCESSOR_ROUTE))
            .routeId(TRACE_PROCESSOR_ROUTE)
            .setBody(constant(DataManagerFactory.getAllDataAdapters()))
            .setHeader(HeaderKey.AdapterCount, simple("${body.size}"))
            .split(body(), new ArrayListAggregationStrategy())
                .parallelProcessing()
                .process(new TracePersonProcessor())
            .end()
            .process(new TracePersonResultProcessor());
    }
}
