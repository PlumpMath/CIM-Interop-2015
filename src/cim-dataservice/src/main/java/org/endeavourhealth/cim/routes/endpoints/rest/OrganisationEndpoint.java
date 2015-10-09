package org.endeavourhealth.cim.routes.endpoints.rest;

import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.routes.routes.OrganisationRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrganisationEndpoint extends CIMRouteBuilder {

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
