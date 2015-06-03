package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.patient.GetChangedPatients;
import org.endeavourhealth.cim.processor.patient.GetPatientRecordByPatientId;
import org.endeavourhealth.cim.processor.patient.GetPatientByIdentifier;

public class PatientEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/{serviceId}/Patient")
            .description("Patient rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get("?identifier={identifier}&_lastUpdated=>{dateUpdated}")
            .route()
            .routeId("GetServicePatientByQuery")
            .description("Query based call")
            .setHeader("MessageRouterCallback", constant("direct:GetPatientByQuery"))
            .to("direct:CIMCore")
        .endRest()

        .get("/{patientId}/$everythingnobinary")
                .route()
                .routeId("GetServicePatientRecordById")
                .description("Direct (patient id) call")
                .setHeader("MessageRouterCallback", constant("direct:GetPatientRecordById"))
                .to("direct:CIMCore")
                .endRest();

        // Message router callback routes
        from("direct:GetPatientByQuery")
            .choice()
                .when(simple("${header.identifier} != null"))
                    .routeId("GetPatientByIdentifier")
                    .process(new GetPatientByIdentifier())
                .when(simple("${header.dateUpdated} != null"))
                    .routeId("GetChangedPatients")
                    .process(new GetChangedPatients())
            .end();

        from("direct:GetPatientRecordById")
            .routeId("GetPatientRecordById")
            .process(new GetPatientRecordByPatientId());
    }
}
