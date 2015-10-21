package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.administrative.GetAppointmentsProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AppointmentRoutes extends BaseRouteBuilder {
	public static final String GET_APPOINTMENTS_ROUTE = "GetPatientAppointments";

    @Override
    public void configureRoute() throws Exception {
        buildCallbackRoute(GET_APPOINTMENTS_ROUTE)
            .process(new GetAppointmentsProcessor());
    }
}
