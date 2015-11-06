package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.routes.SubscriptionRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SubscriptionEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Subscription";

        final String ID_URI = "/{id}";

        rest(BASE_PATH)

        .put(ID_URI)
            .route()
            .routeId(HttpVerb.PUT + BASE_PATH + ID_URI)
            .to(direct(SubscriptionRoutes.ADD_SUBSCRIPTION_ROUTE))
        .endRest();
    }
}
