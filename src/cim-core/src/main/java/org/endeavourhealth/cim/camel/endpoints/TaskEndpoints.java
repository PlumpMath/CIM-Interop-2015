package org.endeavourhealth.cim.camel.endpoints;

import org.endeavourhealth.cim.camel.processors.tasks.AddTaskProcessor;
import org.endeavourhealth.cim.camel.processors.tasks.GetOrganisationTasksProcessor;
import org.endeavourhealth.cim.camel.processors.tasks.GetTaskProcessor;
import org.endeavourhealth.cim.camel.routes.cim.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TaskEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String GET_TASK_ROUTE = "GetTask";
        final String GET_ORGANISATION_TASKS_ROUTE = "GetOrganisationTasks";
        final String POST_TASK_ROUTE = "AddTask";

        final String BASE_PATH = "/{odsCode}/Task";
        final String ID_URI = "/{id}";

        // endpoints
        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
            .to(BaseRouteBuilder.direct(GET_TASK_ROUTE))
        .endRest()

		.get()
			.route()
			.routeId(HttpVerb.GET + BASE_PATH)
			.to(BaseRouteBuilder.direct(GET_ORGANISATION_TASKS_ROUTE))
		.endRest()

		.post()
			.route()
			.routeId(HttpVerb.POST + BASE_PATH)
			.to(BaseRouteBuilder.direct(POST_TASK_ROUTE))
		.endRest();

        // routes
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_TASK_ROUTE)
                .process(new GetTaskProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, GET_ORGANISATION_TASKS_ROUTE)
                .process(new GetOrganisationTasksProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, POST_TASK_ROUTE)
                .process(new AddTaskProcessor());
    }
}
