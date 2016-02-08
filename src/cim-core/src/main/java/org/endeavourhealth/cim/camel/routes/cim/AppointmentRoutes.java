package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.administrative.GetAppointmentsProcessor;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AppointmentRoutes extends BaseRouteBuilder {
	public static final String GET_APPOINTMENTS_ROUTE = "GetPatientAppointments";

    @Override
    public void configureRoute() throws Exception {
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_APPOINTMENTS_ROUTE)
            .process(new GetAppointmentsProcessor());
    }
}
