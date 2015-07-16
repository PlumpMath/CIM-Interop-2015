package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
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
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetPatientByQuery"))
            .to("direct:CIMCore")
        .endRest()

        .get("/{id}/$everythingnobinary")
            .route()
            .routeId("GetServicePatientRecordById")
            .description("Direct (patient id) call")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetPatientRecordById"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetPatientByQuery")
            .choice()
                .when(simple("${header." + HeaderKey.Identifier + "} != null"))
                    .routeId("GetPatientByIdentifier")
                    .process(new GetPatientByIdentifier())
                .when(simple("${header." + HeaderKey.LastUpdated + "} != null"))
                    .routeId("GetChangedPatients")
                    .process(new GetChangedPatients(header(HeaderKey.OdsCode).toString()))
                .when(simple("${header." + HeaderKey.Active + "} == true"))
                    .routeId("GetActivePatients")
                    .process(new GetAllPatients(true))
                .otherwise()
					.routeId("GetAllPatients")
					.process(new GetAllPatients(false))
            .end();

        from("direct:GetPatientRecordById")
            .routeId("GetPatientRecordById")
            .process(new GetPatientRecordByPatientId());
    }
}
