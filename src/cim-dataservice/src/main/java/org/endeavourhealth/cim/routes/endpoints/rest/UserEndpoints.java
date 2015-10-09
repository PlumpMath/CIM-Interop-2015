package org.endeavourhealth.cim.routes.endpoints.rest;

import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.routes.routes.UserRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UserEndpoints extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/User";

		final String ID_URI = "/{id}";
		final String USER_TASK_URI = "/{id}/Task";

        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
			.to(Route.direct(UserRoutes.GET_USER_ROUTE))
        .endRest()

		.get(USER_TASK_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + USER_TASK_URI)
			.to(Route.direct(UserRoutes.GET_USER_TASK_ROUTE))
		.endRest();
    }
}
