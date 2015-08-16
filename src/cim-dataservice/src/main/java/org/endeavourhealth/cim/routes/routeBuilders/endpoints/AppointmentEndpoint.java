package org.endeavourhealth.cim.routes.routeBuilders.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.processor.administrative.GetAppointmentsProcessor;

public class AppointmentEndpoint extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Appointment";
        final String APPOINTMENTS_ROUTE = "?patient={patient}&date={date}";

        final String GET_APPOINTMENTS_PROCESSOR_ROUTE = "GetPatientAppointments";

        rest(BASE_ROUTE)

        .get(APPOINTMENTS_ROUTE)
                .route()
                .routeId(HttpVerb.GET + BASE_ROUTE + APPOINTMENTS_ROUTE)
                .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(GET_APPOINTMENTS_PROCESSOR_ROUTE)))
                .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(GET_APPOINTMENTS_PROCESSOR_ROUTE))
            .routeId(GET_APPOINTMENTS_PROCESSOR_ROUTE)
            .process(new GetAppointmentsProcessor());
    }
}