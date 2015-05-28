package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.patient.GetPatientByPatientId;
import org.endeavourhealth.cim.processor.patient.GetPatientByNHSNo;

public class PatientEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/service/{serviceId}/patient")
            .description("Patient rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get("?nhsNo={nhsNo}")
            .route()
            .routeId("GetServicePatientByQuery")
            .description("Query based call")
            .process(new GetPatientByNHSNo())
            .setHeader("MessageRouterCallback", constant("direct:GetPatientByNHSNo"))
            .to("direct:CIMCore")
        .endRest()

        .get("/{patientId}")
            .route()
            .routeId("GetServicePatientById")
            .description("Direct (patient id) call")
            .setHeader("MessageRouterCallback", constant("direct:GetPatientById"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetPatientByNHSNo")
            .routeId("GetPatientByNHSNo")
            .process(new GetPatientByNHSNo());

        from("direct:GetPatientById")
            .routeId("GetPatientById")
            .process(new GetPatientByPatientId());
    }
}
