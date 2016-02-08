package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.cim.processor.administrative.GetSchedulesProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings("WeakerAccess")
public class ScheduleRoutes extends BaseRouteBuilder {
	public static final String GET_SCHEDULES_ROUTE = "GetSchedules";

    @Override
    public void configureRoute() throws Exception {
		buildWrappedRoute(CimCore.ROUTE_NAME, GET_SCHEDULES_ROUTE)
			.process(new GetSchedulesProcessor());
    }
}
