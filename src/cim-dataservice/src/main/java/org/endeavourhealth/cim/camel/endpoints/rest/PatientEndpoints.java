package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.routes.PatientRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PatientEndpoints extends BaseRouteBuilder {

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
            .to(direct(PatientRoutes.GET_DEMOGRAPHIC_PATIENT_ROUTE))
        .endRest()

        .get(ID_EVERYTHING_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_EVERYTHING_URI)
            .to(direct(PatientRoutes.GET_FULL_PATIENT_ROUTE))
        .endRest()

        .get(BY_QUERY_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + BY_QUERY_URI)
			.to(direct(PatientRoutes.GET_PATIENT_BY_QUERY_ROUTE))
        .endRest()

		.get(TASKS_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + TASKS_URI)
			.to(direct(PatientRoutes.GET_PATIENT_TASKS_ROUTE))
		.endRest();

    }
}
