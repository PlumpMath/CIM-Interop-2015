package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.administrative.AddTaskProcessor;
import org.endeavourhealth.cim.processor.administrative.GetOrganisationTasksProcessor;
import org.endeavourhealth.cim.processor.administrative.GetTaskProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TaskRoutes extends CIMRouteBuilder {
	public static final String GET_TASK_ROUTE = "GetTask";
	public static final String GET_ORGANISATION_TASKS_ROUTE = "GetOrganisationTasks";
	public static final String POST_TASK_ROUTE = "AddTask";

    @Override
    public void configureRoute() throws Exception {
		buildCimCallbackRoute(GET_TASK_ROUTE)
            .process(new GetTaskProcessor());

		buildCimCallbackRoute(GET_ORGANISATION_TASKS_ROUTE)
			.process(new GetOrganisationTasksProcessor());

		buildCimCallbackRoute(POST_TASK_ROUTE)
			.process(new AddTaskProcessor());
    }
}
