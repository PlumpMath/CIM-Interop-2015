package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.administrative.GetAppointmentsProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AppointmentRoutes extends CIMRouteBuilder {
	public static final String GET_APPOINTMENTS_ROUTE = "GetPatientAppointments";

    @Override
    public void configureRoute() throws Exception {
        buildCimCallbackRoute(GET_APPOINTMENTS_ROUTE)
            .process(new GetAppointmentsProcessor());
    }
}
