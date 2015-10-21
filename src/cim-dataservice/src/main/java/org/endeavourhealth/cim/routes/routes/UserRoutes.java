package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.administrative.GetUserProcessor;
import org.endeavourhealth.cim.processor.administrative.GetUserTasksProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UserRoutes extends BaseRouteBuilder {
	public static final String GET_USER_ROUTE = "GetUser";
	public static final String GET_USER_TASK_ROUTE = "GetUserTask";

    @Override
    public void configureRoute() throws Exception {
		buildCallbackRoute(GET_USER_ROUTE)
				.process(new GetUserProcessor());

		buildCallbackRoute(GET_USER_TASK_ROUTE)
				.process(new GetUserTasksProcessor());
	}
}
