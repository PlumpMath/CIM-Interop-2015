package org.endeavourhealth.cim.camel.endpoints;

import org.endeavourhealth.cim.camel.processors.administrative.GetUserProcessor;
import org.endeavourhealth.cim.camel.processors.tasks.GetUserTasksProcessor;
import org.endeavourhealth.cim.camel.routes.cim.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UserEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String GET_USER_ROUTE = "GetUser";
        final String GET_USER_TASK_ROUTE = "GetUserTask";

        final String BASE_PATH = "/{odsCode}/User";
		final String ID_URI = "/{id}";
		final String USER_TASK_URI = "/{id}/Task";

        // endpoints
        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
			.to(BaseRouteBuilder.direct(GET_USER_ROUTE))
        .endRest()

		.get(USER_TASK_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + USER_TASK_URI)
			.to(BaseRouteBuilder.direct(GET_USER_TASK_ROUTE))
		.endRest();

        // routes
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_USER_ROUTE)
                .process(new GetUserProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, GET_USER_TASK_ROUTE)
                .process(new GetUserTasksProcessor());

    }
}
