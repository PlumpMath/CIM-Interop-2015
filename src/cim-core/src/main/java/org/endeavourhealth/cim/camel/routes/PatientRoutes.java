package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.cim.processor.administrative.GetPatientTasksProcessor;
import org.endeavourhealth.cim.processor.clinical.GetFullPatientRecordProcessor;
import org.endeavourhealth.cim.processor.demographics.GetAllPatientsProcessor;
import org.endeavourhealth.cim.processor.demographics.GetDemographicPatientProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PatientRoutes extends BaseRouteBuilder {
	public static final String GET_DEMOGRAPHIC_PATIENT_ROUTE = "GetDemographicPatient";
	public static final String GET_FULL_PATIENT_ROUTE = "GetFullPatient";
	public static final String GET_PATIENT_BY_QUERY_ROUTE = "GetPatientByQuery";
	public static final String GET_PATIENT_TASKS_ROUTE = "GetPatientTasks";

    @Override
    public void configureRoute() throws Exception {
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
