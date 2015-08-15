package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.processor.clinical.AddConditionProcessor;
import org.endeavourhealth.cim.processor.clinical.GetConditionsProcessor;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ConditionEndpoint extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Patient";
        final String CONDITION_ROUTE = "/{id}/Condition";

        final String GET_CONDITION_PROCESSOR_ROUTE = "GetConditionsRoute";
        final String POST_CONDITION_PROCESSOR_ROUTE = "AddConditionRoute";

        rest(BASE_ROUTE)
            .get(BASE_ROUTE + CONDITION_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + CONDITION_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(GET_CONDITION_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest()

        .post()
            .route()
            .routeId(HttpVerb.POST + BASE_ROUTE + CONDITION_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(POST_CONDITION_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(GET_CONDITION_PROCESSOR_ROUTE))
            .routeId(GET_CONDITION_PROCESSOR_ROUTE)
            .process(new GetConditionsProcessor());

        from(Route.direct(POST_CONDITION_PROCESSOR_ROUTE))
            .routeId(POST_CONDITION_PROCESSOR_ROUTE)
            .process(new AddConditionProcessor());
    }
}
