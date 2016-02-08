package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.administrative.GetUserProcessor;
import org.endeavourhealth.cim.camel.processors.administrative.GetUserTasksProcessor;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UserRoutes extends BaseRouteBuilder {
	public static final String GET_USER_ROUTE = "GetUser";
	public static final String GET_USER_TASK_ROUTE = "GetUserTask";

    @Override
    public void configureRoute() throws Exception {
		buildWrappedRoute(CimCore.ROUTE_NAME, GET_USER_ROUTE)
				.process(new GetUserProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME, GET_USER_TASK_ROUTE)
				.process(new GetUserTasksProcessor());
	}
}
