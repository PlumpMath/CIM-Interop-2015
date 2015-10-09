package org.endeavourhealth.cim.routes.endpoints.rest;

import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.routes.routes.AppointmentRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AppointmentEndpoints extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Appointment";

        final String APPOINTMENTS_URI = "?patient={patient}&date={date}";

        rest(BASE_PATH)

        .get(APPOINTMENTS_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + APPOINTMENTS_URI)
            .to(Route.direct(AppointmentRoutes.GET_APPOINTMENTS_ROUTE))
        .endRest();
    }
}
