package org.endeavourhealth.cim.routes.routeBuilders.endpoints;

import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.processor.administrative.GetTaskProcessor;
import org.endeavourhealth.cim.processor.administrative.AddTaskProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TaskEndpoint extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Task";
        final String ID_ROUTE = "/{id}";

        final String ID_PROCESSOR_ROUTE = "GetTask";
		final String POST_PROCESSOR_ROUTE = "AddTask";

        rest(BASE_ROUTE)

        .get(ID_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + ID_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(ID_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest()

		.post()
			.route()
			.routeId(HttpVerb.POST + BASE_ROUTE)
			.setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(POST_PROCESSOR_ROUTE)))
			.to(Route.CIM_CORE);

        from(Route.direct(ID_PROCESSOR_ROUTE))
            .routeId(ID_PROCESSOR_ROUTE)
            .process(new GetTaskProcessor());

		from(Route.direct(POST_PROCESSOR_ROUTE))
			.routeId(POST_PROCESSOR_ROUTE)
			.process(new AddTaskProcessor());
    }
}
