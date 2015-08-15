package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.processor.clinical.GetFullPatientRecordProcessor;
import org.endeavourhealth.cim.processor.demographics.*;

@SuppressWarnings("WeakerAccess")
public class PatientEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        rest("/{odsCode}/Patient")
            .description("Patient rest service")

        .get("/{id}")
            .route()
            .routeId("/{odsCode}/Patient/{id}")
            .description("Get demographics record by patient ID")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetDemographicPatientById"))
            .to("direct:CIMCore")
        .endRest()

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
            .description("Get full record (including clinical) by patient ID")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetPatientRecordById"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetPatientByQuery")
            .choice()
                .when(simple("${header." + HeaderKey.Identifier + "} != null"))
                    .routeId("GetPatientByIdentifier")
                    .process(new GetPatientByIdentifierProcessor())
                .when(simple("${header." + HeaderKey.LastUpdated + "} != null"))
                    .routeId("GetChangedPatients")
                    .process(new GetChangedPatientsProcessor(header(HeaderKey.OdsCode).toString()))
                .when(simple("${header." + HeaderKey.Active + "} == true"))
                    .routeId("GetActivePatients")
                    .process(new GetAllPatientsProcessor(true))
                .otherwise()
					.routeId("GetAllPatients")
					.process(new GetAllPatientsProcessor(false))
            .end();

        from("direct:GetDemographicPatientById")
            .routeId("GetDemographicPatientById")
            .process(new GetDemographicPatientProcessor());

        from("direct:GetPatientRecordById")
            .routeId("GetPatientRecordById")
            .process(new GetFullPatientRecordProcessor());
    }
}
