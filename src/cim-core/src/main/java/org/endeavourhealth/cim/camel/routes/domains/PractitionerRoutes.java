package org.endeavourhealth.cim.camel.routes.domains;

import org.endeavourhealth.cim.camel.processors.administrative.GetAllPractitionersProcessor;
import org.endeavourhealth.cim.camel.processors.administrative.GetPractitionerProcessor;
import org.endeavourhealth.cim.camel.processors.tasks.GetPractitionerTasksProcessor;
import org.endeavourhealth.cim.camel.routes.common.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PractitionerRoutes extends BaseRouteBuilder
{
    @Override
    public void configureRoute() throws Exception
    {
        final String GET_ALL_PRACTITIONERS_ROUTE = "GetAllPractitioners";
        final String GET_PRACTITIONER_ROUTE = "GetPractitioner";
        final String GET_PRACTITIONER_TASK_ROUTE = "GetPractitionerTask";

        final String BASE_PATH = "/{odsCode}/Practitioner";
		final String ID_URI = "/{id}";
		final String PRACTITIONER_TASK_URI = "/{id}/Task";

        // endpoints
        rest(BASE_PATH)

        .get()
            .route()
            .routeId(HttpVerb.GET + BASE_PATH)
            .to(BaseRouteBuilder.direct(GET_ALL_PRACTITIONERS_ROUTE))
        .endRest()

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
			.to(BaseRouteBuilder.direct(GET_PRACTITIONER_ROUTE))
        .endRest()

		.get(PRACTITIONER_TASK_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + PRACTITIONER_TASK_URI)
			.to(BaseRouteBuilder.direct(GET_PRACTITIONER_ROUTE))
		.endRest();

        // routes
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_ALL_PRACTITIONERS_ROUTE)
                .process(new GetAllPractitionersProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, GET_PRACTITIONER_ROUTE)
                .process(new GetPractitionerProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, GET_PRACTITIONER_TASK_ROUTE)
                .process(new GetPractitionerTasksProcessor());

    }
}
