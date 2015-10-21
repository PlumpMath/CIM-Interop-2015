package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.cim.processor.administrative.GetPatientTasksProcessor;
import org.endeavourhealth.cim.processor.clinical.GetFullPatientRecordProcessor;
import org.endeavourhealth.cim.processor.demographics.GetAllPatientsProcessor;
import org.endeavourhealth.cim.processor.demographics.GetChangedPatientsProcessor;
import org.endeavourhealth.cim.processor.demographics.GetDemographicPatientProcessor;
import org.endeavourhealth.cim.processor.demographics.GetPatientByIdentifierProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.HeaderKey;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PatientRoutes extends BaseRouteBuilder {
	public static final String GET_DEMOGRAPHIC_PATIENT_ROUTE = "GetDemographicPatient";
	public static final String GET_FULL_PATIENT_ROUTE = "GetFullPatient";
	public static final String GET_PATIENT_BY_QUERY_ROUTE = "GetPatientByQuery";
	public static final String GET_PATIENT_TASKS_ROUTE = "GetPatientTasks";

    @Override
    public void configureRoute() throws Exception {
		buildCallbackRoute(GET_DEMOGRAPHIC_PATIENT_ROUTE)
			.process(new GetDemographicPatientProcessor());

		buildCallbackRoute(GET_FULL_PATIENT_ROUTE)
			.process(new GetFullPatientRecordProcessor());

		buildCallbackRoute(GET_PATIENT_TASKS_ROUTE)
			.process(new GetPatientTasksProcessor());

		buildCallbackRoute(GET_PATIENT_BY_QUERY_ROUTE)
			.choice()
				.when(simple("${header." + CIMHeaderKey.Identifier + "} != null"))
				.routeId("GetPatientByIdentifier")
				.process(new GetPatientByIdentifierProcessor())
			.when(simple("${header." + CIMHeaderKey.LastUpdated + "} != null"))
				.routeId("GetChangedPatients")
				.process(new GetChangedPatientsProcessor(header(HeaderKey.OdsCode).toString()))
			.when(simple("${header." + CIMHeaderKey.Active + "} == true"))
				.routeId("GetActivePatients")
				.process(new GetAllPatientsProcessor(true))
			.otherwise()
				.routeId("GetAllPatients")
				.process(new GetAllPatientsProcessor(false))
			.end();
    }
}
