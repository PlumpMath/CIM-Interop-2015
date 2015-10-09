package org.endeavourhealth.cim.routes.endpoints.rest;

import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.routes.routes.SubscriptionRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SubscriptionEndpoints extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Subscription";

        final String ID_URI = "/{id}";

        rest(BASE_PATH)

        .put(ID_URI)
            .route()
            .routeId(HttpVerb.PUT + BASE_PATH + ID_URI)
            .to(Route.direct(SubscriptionRoutes.ADD_SUBSCRIPTION_ROUTE))
        .endRest();
    }
}
