package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.event.GetMedicationPrescriptions;

@SuppressWarnings("WeakerAccess")
public class MedicationPrescriptionEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/{odsCode}/Patient/{id}/MedicationPrescription")
            .description("Medication Prescription rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get()
            .route()
            .routeId("GetMedicationPrescriptionsEndPoint")
            .description("Get patient medication prescriptions")
            .setHeader("MessageRouterCallback", constant("direct:GetMedicationPrescriptionsRoute"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetMedicationPrescriptionsRoute")
            .routeId("GetMedicationPrescriptionsRoute")
            .process(new GetMedicationPrescriptions());
    }
}
