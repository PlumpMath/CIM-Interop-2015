package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.routes.ScheduleRoutes;

@SuppressWarnings("WeakerAccess")
public class ScheduleEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Schedule";

        final String GET_SCHEDULES_URI = "?date={date}&actor:Practitioner={practitioner}";

        rest(BASE_PATH)

        .get(GET_SCHEDULES_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + GET_SCHEDULES_URI)
            .to(direct(ScheduleRoutes.GET_SCHEDULES_ROUTE))
        .endRest();
	}
}
