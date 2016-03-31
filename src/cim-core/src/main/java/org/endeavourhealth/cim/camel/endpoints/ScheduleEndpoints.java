package org.endeavourhealth.cim.camel.endpoints;

import org.endeavourhealth.cim.camel.processors.appointments.GetSchedulesProcessor;
import org.endeavourhealth.cim.camel.routes.cim.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings("WeakerAccess")
public class ScheduleEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String GET_SCHEDULES_ROUTE = "GetSchedules";

        final String BASE_PATH = "/{odsCode}/Schedule";
        final String GET_SCHEDULES_URI = "?date={date}&actor:Practitioner={practitioner}";

        // endpoints
        rest(BASE_PATH)

        .get(GET_SCHEDULES_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + GET_SCHEDULES_URI)
            .to(BaseRouteBuilder.direct(GET_SCHEDULES_ROUTE))
                .endRest();

        // routes
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_SCHEDULES_ROUTE)
                .process(new GetSchedulesProcessor());
	}
}
