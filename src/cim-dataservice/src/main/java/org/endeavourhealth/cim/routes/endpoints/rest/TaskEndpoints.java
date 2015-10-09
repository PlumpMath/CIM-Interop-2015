package org.endeavourhealth.cim.routes.endpoints.rest;

import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.routes.routes.TaskRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TaskEndpoints extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Task";

        final String ID_URI = "/{id}";

        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
            .to(Route.direct(TaskRoutes.GET_TASK_ROUTE))
        .endRest()

		.get()
			.route()
			.routeId(HttpVerb.GET + BASE_PATH)
			.to(Route.direct(TaskRoutes.GET_ORGANISATION_TASKS_ROUTE))
		.endRest()

		.post()
			.route()
			.routeId(HttpVerb.POST + BASE_PATH)
			.to(Route.direct(TaskRoutes.POST_TASK_ROUTE))
		.endRest();
    }
}
