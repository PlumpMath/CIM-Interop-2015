package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.processor.core.CIMError;
import org.endeavourhealth.cim.processor.administrative.GetAppointmentsProcessor;

public class AppointmentEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("{odsCode}/Appointment?patient={patient}&date={date}")
                .description("Appointment rest service")

        // Endpoint definitions (GET, PUT, etc)
                .get()
                .route()
                .routeId("GetServicePatientAppointments")
                .description("Get Patient Appointments By Patient ID")
                .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetPatientAppointments"))
                .to("direct:CIMCore")
                .endRest();

        // Message router callback routes
        from("direct:GetPatientAppointments")
                .routeId("GetPatientAppointments")
                .doTry()
                .process(new GetAppointmentsProcessor())
                .doCatch(IllegalArgumentException.class)
                .process(new CIMError(HttpStatus.SC_BAD_REQUEST, simple("${exception.message}")))
                .to("direct:CIMInvalidMessage")
                .end();
    }
}
