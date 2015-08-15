package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.processor.demographics.AddSubscriptionProcessor;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings("WeakerAccess")
public class SubscriptionEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Subscription";
        final String ID_ROUTE = "/{id}";

        final String PUT_SUBSCRIPTION_BY_ID_PROCESSOR_ROUTE = "AddSubscription";

        rest(BASE_ROUTE)

        .put(ID_ROUTE)
            .route()
            .routeId(HttpVerb.PUT + BASE_ROUTE + ID_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(PUT_SUBSCRIPTION_BY_ID_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(PUT_SUBSCRIPTION_BY_ID_PROCESSOR_ROUTE))
            .routeId(PUT_SUBSCRIPTION_BY_ID_PROCESSOR_ROUTE)
            .process(new AddSubscriptionProcessor());
    }
}
