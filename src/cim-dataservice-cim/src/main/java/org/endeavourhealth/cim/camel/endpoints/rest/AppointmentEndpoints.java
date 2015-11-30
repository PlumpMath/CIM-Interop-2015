package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.cim.camel.routes.AppointmentRoutes;
import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AppointmentEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Appointment";

        final String APPOINTMENTS_URI = "?patient={patient}&date={date}";

        rest(BASE_PATH)

        .get(APPOINTMENTS_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + APPOINTMENTS_URI)
            .to(BaseRouteBuilder.direct(AppointmentRoutes.GET_APPOINTMENTS_ROUTE))
        .endRest();
    }
}
