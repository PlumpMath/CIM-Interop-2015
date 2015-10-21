package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.administrative.GetSchedulesProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings("WeakerAccess")
public class ScheduleRoutes extends BaseRouteBuilder {
	public static final String GET_SCHEDULES_ROUTE = "GetSchedules";

    @Override
    public void configureRoute() throws Exception {
		buildCallbackRoute(GET_SCHEDULES_ROUTE)
			.process(new GetSchedulesProcessor());
    }
}
