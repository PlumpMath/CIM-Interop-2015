package org.endeavourhealth.cim.routes.endpoints.rest;

import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.routes.routes.PatientRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PatientEndpoints extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Patient";

        final String ID_DEMOGRAPHIC_URI = "/{id}";
        final String ID_EVERYTHING_URI = "/{id}/$everythingnobinary";
		final String TASKS_URI = "/{id}/Task";
        final String BY_QUERY_URI = "?identifier={identifier}&_lastUpdated=>{dateUpdated}&active={active}";

        rest(BASE_PATH)

        .get(ID_DEMOGRAPHIC_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_DEMOGRAPHIC_URI)
            .to(Route.direct(PatientRoutes.GET_DEMOGRAPHIC_PATIENT_ROUTE))
        .endRest()

        .get(ID_EVERYTHING_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_EVERYTHING_URI)
            .to(Route.direct(PatientRoutes.GET_FULL_PATIENT_ROUTE))
        .endRest()

        .get(BY_QUERY_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + BY_QUERY_URI)
			.to(Route.direct(PatientRoutes.GET_PATIENT_BY_QUERY_ROUTE))
        .endRest()

		.get(TASKS_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + TASKS_URI)
			.to(Route.direct(PatientRoutes.GET_PATIENT_TASKS_ROUTE))
		.endRest();

    }
}
