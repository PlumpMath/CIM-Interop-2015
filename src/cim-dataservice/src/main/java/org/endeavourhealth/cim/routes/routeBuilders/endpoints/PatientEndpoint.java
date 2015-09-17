package org.endeavourhealth.cim.routes.routeBuilders.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.processor.administrative.GetPatientTasksProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.processor.clinical.GetFullPatientRecordProcessor;
import org.endeavourhealth.cim.processor.demographics.*;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PatientEndpoint extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Patient";
        final String ID_DEMOGRAPHIC_ROUTE = "/{id}";
        final String ID_EVERYTHING_ROUTE = "/{id}/$everythingnobinary";
		final String TASKS_ROUTE = "/{id}/Task";
        final String BY_QUERY_ROUTE = "?identifier={identifier}&_lastUpdated=>{dateUpdated}&active={active}";

        final String ID_DEMOGRAPHIC_PROCESSOR_ROUTE = "GetDemographicPatient";
        final String ID_EVERYTHING_PROCESSOR_ROUTE = "GetFullPatient";
		final String BY_QUERY_PROCESSOR_ROUTE = "GetPatientByQuery";
		final String TASKS_PROCESSOR_ROUTE = "GetPatientTasks";

        rest(BASE_ROUTE)

        .get(ID_DEMOGRAPHIC_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + ID_DEMOGRAPHIC_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(ID_DEMOGRAPHIC_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest()

        .get(ID_EVERYTHING_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + ID_EVERYTHING_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(ID_EVERYTHING_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest()

        .get(BY_QUERY_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + BY_QUERY_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(BY_QUERY_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest()

		.get(TASKS_ROUTE)
			.route()
			.routeId(HttpVerb.GET + BASE_ROUTE + TASKS_ROUTE)
			.setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(TASKS_PROCESSOR_ROUTE)))
			.to(Route.CIM_CORE)
		.endRest();

        from(Route.direct(ID_DEMOGRAPHIC_PROCESSOR_ROUTE))
            .routeId(ID_DEMOGRAPHIC_PROCESSOR_ROUTE)
            .process(new GetDemographicPatientProcessor());

        from(Route.direct(ID_EVERYTHING_PROCESSOR_ROUTE))
            .routeId(ID_EVERYTHING_PROCESSOR_ROUTE)
            .process(new GetFullPatientRecordProcessor());

		from(Route.direct(TASKS_PROCESSOR_ROUTE))
			.routeId(TASKS_PROCESSOR_ROUTE)
			.process(new GetPatientTasksProcessor());

        from(Route.direct(BY_QUERY_PROCESSOR_ROUTE))
			.routeId(BY_QUERY_PROCESSOR_ROUTE)
            .choice()
                .when(simple("${header." + HeaderKey.Identifier + "} != null"))
                    .routeId("GetPatientByIdentifier")
                    .process(new GetPatientByIdentifierProcessor())
                .when(simple("${header." + HeaderKey.LastUpdated + "} != null"))
                    .routeId("GetChangedPatients")
                    .process(new GetChangedPatientsProcessor(header(HeaderKey.OdsCode).toString()))
                .when(simple("${header." + HeaderKey.Active + "} == true"))
                    .routeId("GetActivePatients")
                    .process(new GetAllPatientsProcessor(true))
                .otherwise()
					.routeId("GetAllPatients")
					.process(new GetAllPatientsProcessor(false))
            .end();
    }
}
