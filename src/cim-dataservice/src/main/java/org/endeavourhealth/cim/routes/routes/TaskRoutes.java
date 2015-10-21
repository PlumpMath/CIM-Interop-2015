package org.endeavourhealth.cim.routes.routes;

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
		buildCallbackRoute(GET_TASK_ROUTE)
            .process(new GetTaskProcessor());

		buildCallbackRoute(GET_ORGANISATION_TASKS_ROUTE)
			.process(new GetOrganisationTasksProcessor());

		buildCallbackRoute(POST_TASK_ROUTE)
			.process(new AddTaskProcessor());
    }
}
