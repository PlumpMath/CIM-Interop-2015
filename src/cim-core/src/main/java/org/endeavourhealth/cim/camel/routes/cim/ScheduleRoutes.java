package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.appointments.GetSchedulesProcessor;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings("WeakerAccess")
public class ScheduleRoutes extends BaseRouteBuilder {
	public static final String GET_SCHEDULES_ROUTE = "GetSchedules";

    @Override
    public void configureRoute() throws Exception {
		buildWrappedRoute(CimCore.ROUTE_NAME, GET_SCHEDULES_ROUTE)
			.process(new GetSchedulesProcessor());
    }
}
