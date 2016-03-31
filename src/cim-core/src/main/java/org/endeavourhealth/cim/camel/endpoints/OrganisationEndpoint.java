package org.endeavourhealth.cim.camel.endpoints;

import org.endeavourhealth.cim.camel.processors.administrative.GetOrganisationProcessor;
import org.endeavourhealth.cim.camel.processors.administrative.SearchOrganisationProcessor;
import org.endeavourhealth.cim.camel.routes.cim.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrganisationEndpoint extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String GET_ORGANISATION_ROUTE = "GetOrganisation";
        final String SEARCH_ORGANISATION_ROUTE = "SearchOrganisation";

        final String BASE_PATH = "/Organization";
        final String ID_URI = "/{id}";
        final String IDENTIFIER_URI = "?identifier={identifier}";

        // endpoints
        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
            .to(BaseRouteBuilder.direct(GET_ORGANISATION_ROUTE))
        .endRest()

        .get(IDENTIFIER_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + IDENTIFIER_URI)
            .to(BaseRouteBuilder.direct(SEARCH_ORGANISATION_ROUTE))
        .endRest();

        // routes
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_ORGANISATION_ROUTE)
                .process(new GetOrganisationProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, SEARCH_ORGANISATION_ROUTE)
                .process(new SearchOrganisationProcessor());
    }
}
