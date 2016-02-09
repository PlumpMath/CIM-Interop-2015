package org.endeavourhealth.cim.camel.endpoints;

import org.endeavourhealth.cim.camel.routes.cim.SubscriptionRoutes;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

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
            .to(BaseRouteBuilder.direct(SubscriptionRoutes.ADD_SUBSCRIPTION_ROUTE))
        .endRest();
    }
}