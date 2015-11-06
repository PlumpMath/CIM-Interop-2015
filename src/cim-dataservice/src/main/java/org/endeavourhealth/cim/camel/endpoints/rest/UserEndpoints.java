package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.routes.UserRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UserEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/User";

		final String ID_URI = "/{id}";
		final String USER_TASK_URI = "/{id}/Task";

        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
			.to(direct(UserRoutes.GET_USER_ROUTE))
        .endRest()

		.get(USER_TASK_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + USER_TASK_URI)
			.to(direct(UserRoutes.GET_USER_TASK_ROUTE))
		.endRest();
    }
}
