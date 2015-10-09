package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.administrative.GetSchedulesProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;

@SuppressWarnings("WeakerAccess")
public class ScheduleRoutes extends CIMRouteBuilder {
	public static final String GET_SCHEDULES_ROUTE = "GetSchedules";

    @Override
    public void configureRoute() throws Exception {
		buildCimCallbackRoute(GET_SCHEDULES_ROUTE)
			.process(new GetSchedulesProcessor());
    }
}
