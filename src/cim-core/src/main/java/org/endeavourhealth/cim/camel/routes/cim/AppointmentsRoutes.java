package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.appointments.GetAppointmentsProcessor;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.routes.common.CimCore;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AppointmentsRoutes extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String GET_APPOINTMENTS_ROUTE = "GetPatientAppointments";

        final String BASE_PATH = "/{odsCode}/Appointment";
        final String APPOINTMENTS_URI = "?patient={patient}&date={date}";

        // endpoints
        rest(BASE_PATH)

        .get(APPOINTMENTS_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + APPOINTMENTS_URI)
            .to(BaseRouteBuilder.direct(GET_APPOINTMENTS_ROUTE))
        .endRest();

        // routes
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_APPOINTMENTS_ROUTE)
                .process(new GetAppointmentsProcessor());
    }
}
