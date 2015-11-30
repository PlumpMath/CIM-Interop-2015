package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.cim.processor.administrative.GetAppointmentsProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AppointmentRoutes extends BaseRouteBuilder {
	public static final String GET_APPOINTMENTS_ROUTE = "GetPatientAppointments";

    @Override
    public void configureRoute() throws Exception {
        buildCallbackRoute(CimCore.ROUTE_NAME, GET_APPOINTMENTS_ROUTE)
            .process(new GetAppointmentsProcessor());
    }
}
