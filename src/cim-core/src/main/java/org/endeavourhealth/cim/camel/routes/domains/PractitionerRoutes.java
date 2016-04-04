package org.endeavourhealth.cim.camel.routes.domains;

import org.endeavourhealth.cim.camel.processors.administrative.GetPractitionerProcessor;
import org.endeavourhealth.cim.camel.processors.tasks.GetPractitionerTasksProcessor;
import org.endeavourhealth.cim.camel.routes.common.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PractitionerRoutes extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String GET_PRACTITIONER_ROUTE = "GetPractitioner";
        final String GET_PRACTITIONER_TASK_ROUTE = "GetPractitionerTask";

        final String BASE_PATH = "/{odsCode}/Practitioner";
		final String ID_URI = "/{id}";
		final String USER_TASK_URI = "/{id}/Task";

        // endpoints
        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
			.to(BaseRouteBuilder.direct(GET_PRACTITIONER_ROUTE))
        .endRest()

		.get(USER_TASK_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + USER_TASK_URI)
			.to(BaseRouteBuilder.direct(GET_PRACTITIONER_ROUTE))
		.endRest();

        // routes
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_PRACTITIONER_ROUTE)
                .process(new GetPractitionerProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, GET_PRACTITIONER_TASK_ROUTE)
                .process(new GetPractitionerTasksProcessor());

    }
}
