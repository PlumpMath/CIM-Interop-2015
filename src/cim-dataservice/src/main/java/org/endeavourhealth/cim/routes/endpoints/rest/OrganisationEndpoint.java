package org.endeavourhealth.cim.routes.endpoints.rest;

import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.Route;
import org.endeavourhealth.cim.routes.routes.OrganisationRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrganisationEndpoint extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}";

        rest(BASE_PATH)

        .get()
            .route()
            .routeId(HttpVerb.GET + BASE_PATH)
            .to(Route.direct(OrganisationRoutes.GET_ORGANISATION_ROUTE))
        .endRest();
    }
}
