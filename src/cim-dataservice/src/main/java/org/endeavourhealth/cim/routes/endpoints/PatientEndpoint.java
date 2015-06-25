package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.patient.*;

@SuppressWarnings("WeakerAccess")
public class PatientEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/{odsCode}/Patient")
            .description("Patient rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get("?identifier={identifier}&_lastUpdated=>{dateUpdated}&active={active}")
            .route()
            .routeId("GetServicePatientByQuery")
            .description("Query based call")
            .setHeader("MessageRouterCallback", constant("direct:GetPatientByQuery"))
            .to("direct:CIMCore")
        .endRest()

        .get("/{id}/$everythingnobinary")
            .route()
            .routeId("GetServicePatientRecordById")
            .description("Direct (patient id) call")
            .setHeader("MessageRouterCallback", constant("direct:GetPatientRecordById"))
            .to("direct:CIMCore")
        .endRest()

        .get("{id}/Appointment?status={status}&start={start}&end={end}")
            .route()
            .routeId("GetServicePatientAppointments")
            .description("Get Patient Appointments By Patient ID")
            .setHeader("MessageRouterCallback", constant("direct:GetPatientAppointments"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetPatientByQuery")
            .choice()
                .when(simple("${header.identifier} != null"))
                    .routeId("GetPatientByIdentifier")
                    .process(new GetPatientByIdentifier())
                .when(simple("${header._lastUpdated} != null"))
                    .routeId("GetChangedPatients")
                    .process(new GetChangedPatients(header("odsCode").toString()))
                .when(simple("${header.active} == true"))
                    .routeId("GetActivePatients")
                    .process(new GetAllPatients(true))
                .otherwise()
					.routeId("GetAllPatients")
					.process(new GetAllPatients(false))
            .end();

        from("direct:GetPatientRecordById")
            .routeId("GetPatientRecordById")
            .process(new GetPatientRecordByPatientId());

        from("direct:GetPatientAppointments")
            .routeId("GetPatientAppointments")
            .process(new GetPatientAppointments());
    }
}
