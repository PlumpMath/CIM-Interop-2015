package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.processors.clinical.GetFullPatientRecordProcessor;
import org.endeavourhealth.cim.camel.processors.demographics.GetAllPatientsProcessor;
import org.endeavourhealth.cim.camel.processors.demographics.GetDemographicPatientProcessor;
import org.endeavourhealth.cim.camel.processors.tasks.GetPatientTasksProcessor;
import org.endeavourhealth.cim.camel.routes.common.CimCore;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PatientRoutes extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String GET_DEMOGRAPHIC_PATIENT_ROUTE = "GetDemographicPatient";
        final String GET_FULL_PATIENT_ROUTE = "GetFullPatient";
        final String GET_PATIENT_BY_QUERY_ROUTE = "GetPatientByQuery";
        final String GET_PATIENT_TASKS_ROUTE = "GetPatientTasks";

        final String BASE_PATH = "/{odsCode}/Patient";
        final String ID_DEMOGRAPHIC_URI = "/{id}";
        final String ID_EVERYTHING_URI = "/{id}/$everythingnobinary";
		final String TASKS_URI = "/{id}/Task";
        final String BY_QUERY_URI = "?active={active}&_lastUpdated=>{dateUpdated}";

        // endpoints
        rest(BASE_PATH)

        .get(ID_DEMOGRAPHIC_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_DEMOGRAPHIC_URI)
            .to(BaseRouteBuilder.direct(GET_DEMOGRAPHIC_PATIENT_ROUTE))
        .endRest()

        .get(ID_EVERYTHING_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_EVERYTHING_URI)
            .to(BaseRouteBuilder.direct(GET_FULL_PATIENT_ROUTE))
        .endRest()

        .get(BY_QUERY_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + BY_QUERY_URI)
			.to(BaseRouteBuilder.direct(GET_PATIENT_BY_QUERY_ROUTE))
        .endRest()

		.get(TASKS_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + TASKS_URI)
			.to(BaseRouteBuilder.direct(GET_PATIENT_TASKS_ROUTE))
		.endRest();

        // routes
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_DEMOGRAPHIC_PATIENT_ROUTE)
                .process(new GetDemographicPatientProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, GET_FULL_PATIENT_ROUTE)
                .process(new GetFullPatientRecordProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, GET_PATIENT_TASKS_ROUTE)
                .process(new GetPatientTasksProcessor());

        buildWrappedRoute(CimCore.ROUTE_NAME, GET_PATIENT_BY_QUERY_ROUTE)
                .process(new GetAllPatientsProcessor());
    }
}
