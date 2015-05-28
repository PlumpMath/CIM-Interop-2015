package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.patient.GetPatientByPatientId;
import org.endeavourhealth.cim.processor.patient.PatientIdByNhsNoLookup;

public class PatientEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        rest("/service/{serviceId}/patient")
            .description("Patient rest service")

        .get("?nhsNo={nhsNo}")
            .route()
            .routeId("GetServicePatientByQuery")
            .description("Query based call")
            .process(new PatientIdByNhsNoLookup())
            .setHeader("MessageRouterRecipient", constant("direct:GetPatient"))
            .to("direct:CIMCore")
        .endRest()

        .get("/{patientId}")
            .route()
            .routeId("GetServicePatientById")
            .description("Direct (patient id) call")
            .setHeader("MessageRouterRecipient", constant("direct:GetPatient"))
            .to("direct:CIMCore")
        .endRest();

        from("direct:GetPatient")
            .routeId("GetPatient")
//            .throttle(50)
//                .timePeriodMillis(1000)
            .process(new GetPatientByPatientId());
    }
}
