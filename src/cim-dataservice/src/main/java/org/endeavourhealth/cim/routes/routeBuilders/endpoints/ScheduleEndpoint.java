package org.endeavourhealth.cim.routes.routeBuilders.endpoints;

import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.processor.administrative.GetSchedulesProcessor;

@SuppressWarnings("WeakerAccess")
public class ScheduleEndpoint extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Schedule";
        final String GET_SCHEDULES_ROUTE = "?date={date}&actor:Practitioner={practitioner}";

        final String GET_SCHEDULES_PROCESSOR_ROUTE = "GetSchedules";

        rest(BASE_ROUTE)

        .get(GET_SCHEDULES_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + GET_SCHEDULES_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(GET_SCHEDULES_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(GET_SCHEDULES_PROCESSOR_ROUTE))
            .routeId(GET_SCHEDULES_PROCESSOR_ROUTE)
            .process(new GetSchedulesProcessor());
    }
}
