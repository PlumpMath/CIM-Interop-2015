package org.endeavourhealth.cim.camel.endpoints;

import org.endeavourhealth.cim.camel.routes.cim.OrganisationRoutes;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrganisationEndpoint extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String BASE_PATH = "/Organization";
        final String ID_URI = "/{id}";
        final String IDENTIFIER_URI = "?identifier={identifier}";

        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
            .to(BaseRouteBuilder.direct(OrganisationRoutes.GET_ORGANISATION_BY_ID_ROUTE))
        .endRest()

        .get(IDENTIFIER_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + IDENTIFIER_URI)
            .to(BaseRouteBuilder.direct(OrganisationRoutes.GET_ORGANISATION_ROUTE))
        .endRest();
    }
}
