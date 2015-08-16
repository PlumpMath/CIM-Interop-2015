package org.endeavourhealth.cim.routes.routeBuilders.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.processor.clinical.GetMedicationPrescriptionsProcessor;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("WeakerAccess")
public class MedicationPrescriptionEndpoint extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Patient";
        final String PRESCRIPTIONS_ROUTE = "/{id}/MedicationPrescription";

        final String GET_PRESCRIPTIONS_PROCESSOR_ROUTE = "GetMedicationPrescriptions";

        rest(BASE_ROUTE)

        .get(PRESCRIPTIONS_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + PRESCRIPTIONS_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(GET_PRESCRIPTIONS_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(GET_PRESCRIPTIONS_PROCESSOR_ROUTE))
            .routeId(GET_PRESCRIPTIONS_PROCESSOR_ROUTE)
            .process(new GetMedicationPrescriptionsProcessor());
    }
}
