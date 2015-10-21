package org.endeavourhealth.cim.routes.endpoints.rest;

import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.Route;
import org.endeavourhealth.cim.routes.routes.ScheduleRoutes;

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
            .to(Route.direct(ScheduleRoutes.GET_SCHEDULES_ROUTE))
        .endRest();
	}
}
