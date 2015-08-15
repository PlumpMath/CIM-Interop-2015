package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.common.ArrayListAggregationStrategy;
import org.endeavourhealth.cim.processor.demographics.*;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings("WeakerAccess")
public class PersonEndpoint extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final String BASE_ROUTE = "/Person";
        final String TRACE_ROUTE = "/$trace?identifier=NHS|{nhsNumber}&surname={surname}&dob={dob}&gender={gender}";

        final String TRACE_PROCESSOR_ROUTE = "TracePerson";
        final String TRACE_BY_DEMOGRAPHICS_PROCESSOR_ROUTE = "TraceByDemographics";
        final String TRACE_BY_NHS_NUMBER_PROCESSOR_ROUTE = "TraceByNhsNumber";

        rest(BASE_ROUTE)

        .get(TRACE_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + TRACE_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(TRACE_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(TRACE_PROCESSOR_ROUTE))
                .choice()
                .when(simple("${header." + HeaderKey.Identifier + "} != null"))
                    .to(Route.direct(TRACE_BY_NHS_NUMBER_PROCESSOR_ROUTE))
                .otherwise()
                    .to(Route.direct(TRACE_BY_DEMOGRAPHICS_PROCESSOR_ROUTE))
                .end();

        from(Route.direct(TRACE_BY_DEMOGRAPHICS_PROCESSOR_ROUTE))
            .routeId(TRACE_BY_DEMOGRAPHICS_PROCESSOR_ROUTE)
            .setBody(constant(DataManagerFactory.getAllDataAdapters()))
            .setHeader(HeaderKey.AdapterCount, simple("${body.size}"))
            .split(body(), new ArrayListAggregationStrategy())
                .parallelProcessing()
                .process(new TracePersonByDemographicsProcessor())
            .end()
            .process(new TracePersonResultProcessor());

        from(Route.direct(TRACE_BY_NHS_NUMBER_PROCESSOR_ROUTE))
            .routeId(TRACE_BY_NHS_NUMBER_PROCESSOR_ROUTE)
            .setBody(constant(DataManagerFactory.getAllDataAdapters()))
            .setHeader(HeaderKey.AdapterCount, simple("${body.size}"))
            .split(body(), new ArrayListAggregationStrategy())
                .parallelProcessing()
                .process(new TracePersonByNhsNumberProcessor())
            .end()
            .process(new TracePersonResultProcessor());

    }
}
