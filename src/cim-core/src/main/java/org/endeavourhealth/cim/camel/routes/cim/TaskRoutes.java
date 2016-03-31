package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.tasks.GetTaskProcessor;
import org.endeavourhealth.cim.camel.processors.tasks.AddTaskProcessor;
import org.endeavourhealth.cim.camel.processors.tasks.GetOrganisationTasksProcessor;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TaskRoutes extends BaseRouteBuilder {
	public static final String GET_TASK_ROUTE = "GetTask";
	public static final String GET_ORGANISATION_TASKS_ROUTE = "GetOrganisationTasks";
	public static final String POST_TASK_ROUTE = "AddTask";

    @Override
    public void configureRoute() throws Exception {
		buildWrappedRoute(CimCore.ROUTE_NAME, GET_TASK_ROUTE)
            .process(new GetTaskProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME, GET_ORGANISATION_TASKS_ROUTE)
			.process(new GetOrganisationTasksProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME, POST_TASK_ROUTE)
			.process(new AddTaskProcessor());
    }
}
