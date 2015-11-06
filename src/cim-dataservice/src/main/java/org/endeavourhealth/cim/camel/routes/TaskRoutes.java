package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.cim.processor.administrative.AddTaskProcessor;
import org.endeavourhealth.cim.processor.administrative.GetOrganisationTasksProcessor;
import org.endeavourhealth.cim.processor.administrative.GetTaskProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TaskRoutes extends BaseRouteBuilder {
	public static final String GET_TASK_ROUTE = "GetTask";
	public static final String GET_ORGANISATION_TASKS_ROUTE = "GetOrganisationTasks";
	public static final String POST_TASK_ROUTE = "AddTask";

    @Override
    public void configureRoute() throws Exception {
		buildCallbackRoute(CimCore.ROUTE_NAME, GET_TASK_ROUTE)
            .process(new GetTaskProcessor());

		buildCallbackRoute(CimCore.ROUTE_NAME, GET_ORGANISATION_TASKS_ROUTE)
			.process(new GetOrganisationTasksProcessor());

		buildCallbackRoute(CimCore.ROUTE_NAME, POST_TASK_ROUTE)
			.process(new AddTaskProcessor());
    }
}
