package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.config.Route;
import org.endeavourhealth.cim.processor.clinical.GetFullPatientRecordProcessor;
import org.endeavourhealth.cim.processor.demographics.*;

@SuppressWarnings("WeakerAccess")
public class PatientEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Patient";
        final String ID_DEMOGRAPHIC_ROUTE = "/{id}";
        final String ID_EVERYTHING_ROUTE = "/{id}/$everythingnobinary";
        final String NHS_NUMBER_ROUTE = "?identifier={identifier}&_lastUpdated=>{dateUpdated}&active={active}";

        final String ID_DEMOGRAPHIC_PROCESSOR_ROUTE = "GetDemographicPatient";
        final String ID_EVERYTHING_PROCESSOR_ROUTE = "GetFullPatient";

        rest(BASE_ROUTE)

        .get(ID_DEMOGRAPHIC_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + ID_DEMOGRAPHIC_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(ID_DEMOGRAPHIC_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest()

        .get(ID_EVERYTHING_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + ID_EVERYTHING_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(ID_EVERYTHING_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest()

        .get(NHS_NUMBER_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + NHS_NUMBER_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetPatientByQuery"))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(ID_DEMOGRAPHIC_PROCESSOR_ROUTE))
            .routeId(ID_DEMOGRAPHIC_PROCESSOR_ROUTE)
            .process(new GetDemographicPatientProcessor());

        from(Route.direct(ID_EVERYTHING_PROCESSOR_ROUTE))
            .routeId(ID_EVERYTHING_PROCESSOR_ROUTE)
            .process(new GetFullPatientRecordProcessor());

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
    }
}
