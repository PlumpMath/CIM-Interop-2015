package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.routes.TaskRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TaskEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Task";

        final String ID_URI = "/{id}";

        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
            .to(direct(TaskRoutes.GET_TASK_ROUTE))
        .endRest()

		.get()
			.route()
			.routeId(HttpVerb.GET + BASE_PATH)
			.to(direct(TaskRoutes.GET_ORGANISATION_TASKS_ROUTE))
		.endRest()

		.post()
			.route()
			.routeId(HttpVerb.POST + BASE_PATH)
			.to(direct(TaskRoutes.POST_TASK_ROUTE))
		.endRest();
    }
}
