package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;
import org.endeavourhealth.cim.processor.core.CIMError;
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
            .doTry()
                .process(new GetAppointmentsProcessor())
            .doCatch(IllegalArgumentException.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("${exception.message}")))
                .to(Route.direct(CoreRouteName.CIM_INVALID_MESSAGE))
            .end();
    }
}
