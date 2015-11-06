package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.routes.PersonRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PersonEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/Person";

        final String TRACE_URI = "?identifier={nhsNumber}&name={name}&dob={dob}&gender={gender}";

        rest(BASE_PATH)

        .get(TRACE_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + TRACE_URI)
            .to(direct(PersonRoutes.TRACE_PERSON_ROUTE))
        .endRest();
    }
}
