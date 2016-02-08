package org.endeavourhealth.cim.camel.endpoints;

import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.routes.cim.PatientRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PatientEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Patient";

        final String ID_DEMOGRAPHIC_URI = "/{id}";
        final String ID_EVERYTHING_URI = "/{id}/$everythingnobinary";
		final String TASKS_URI = "/{id}/Task";
        final String BY_QUERY_URI = "?active={active}&_lastUpdated=>{dateUpdated}";

        rest(BASE_PATH)

        .get(ID_DEMOGRAPHIC_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_DEMOGRAPHIC_URI)
            .to(BaseRouteBuilder.direct(PatientRoutes.GET_DEMOGRAPHIC_PATIENT_ROUTE))
        .endRest()

        .get(ID_EVERYTHING_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_EVERYTHING_URI)
            .to(BaseRouteBuilder.direct(PatientRoutes.GET_FULL_PATIENT_ROUTE))
        .endRest()

        .get(BY_QUERY_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + BY_QUERY_URI)
			.to(BaseRouteBuilder.direct(PatientRoutes.GET_PATIENT_BY_QUERY_ROUTE))
        .endRest()

		.get(TASKS_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + TASKS_URI)
			.to(BaseRouteBuilder.direct(PatientRoutes.GET_PATIENT_TASKS_ROUTE))
		.endRest();

    }
}
