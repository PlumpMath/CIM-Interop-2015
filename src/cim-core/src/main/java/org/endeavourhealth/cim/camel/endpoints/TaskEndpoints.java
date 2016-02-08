package org.endeavourhealth.cim.camel.endpoints;

import org.endeavourhealth.cim.camel.routes.cim.TaskRoutes;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

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
            .to(BaseRouteBuilder.direct(TaskRoutes.GET_TASK_ROUTE))
        .endRest()

		.get()
			.route()
			.routeId(HttpVerb.GET + BASE_PATH)
			.to(BaseRouteBuilder.direct(TaskRoutes.GET_ORGANISATION_TASKS_ROUTE))
		.endRest()

		.post()
			.route()
			.routeId(HttpVerb.POST + BASE_PATH)
			.to(BaseRouteBuilder.direct(TaskRoutes.POST_TASK_ROUTE))
		.endRest();
    }
}
