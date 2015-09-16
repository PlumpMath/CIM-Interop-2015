package org.endeavourhealth.cim.routes.routeBuilders.endpoints;

import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.processor.administrative.GetUserProcessor;
import org.endeavourhealth.cim.processor.administrative.GetUserTasksProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UserEndpoint extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/User";
        final String ID_ROUTE = "/{id}";
		final String USER_TASK_ROUTE = "/{id}/Task";

        final String ID_PROCESSOR_ROUTE = "GetUser";
		final String USER_TASK_PROCESSOR_ROUTE = "GetUserTask";

        rest(BASE_ROUTE)

        .get(ID_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + ID_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(ID_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest()

		.get(USER_TASK_ROUTE)
			.route()
			.routeId(HttpVerb.GET + BASE_ROUTE + USER_TASK_ROUTE)
			.setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(USER_TASK_PROCESSOR_ROUTE)))
			.to(Route.CIM_CORE)
		.endRest();

        from(Route.direct(ID_PROCESSOR_ROUTE))
            .routeId(ID_PROCESSOR_ROUTE)
            .process(new GetUserProcessor());

		from(Route.direct(USER_TASK_PROCESSOR_ROUTE))
			.routeId(USER_TASK_PROCESSOR_ROUTE)
			.process(new GetUserTasksProcessor());
    }
}
