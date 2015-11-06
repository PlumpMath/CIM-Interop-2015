package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.routes.ClinicalRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ClinicalEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Patient";

        final String ALLERGY_INTOLERANCE_URI = "/{id}/AllergyIntolerance";
		final String CONDITION_URI = "/{id}/Condition";
		final String IMMUNIZATIONS_URI = "/{id}/Immunization";
		final String PRESCRIPTIONS_URI = "/{id}/MedicationPrescription";

        rest(BASE_PATH)

        .get(ALLERGY_INTOLERANCE_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ALLERGY_INTOLERANCE_URI)
			.to(direct(ClinicalRoutes.GET_ALLERGY_INTOLERANCES_ROUTE))
        .endRest()

		.get(CONDITION_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + CONDITION_URI)
			.to(direct(ClinicalRoutes.GET_CONDITION_ROUTE))
		.endRest()

		.post(CONDITION_URI)
			.route()
			.routeId(HttpVerb.POST + BASE_PATH + CONDITION_URI)
			.to(direct(ClinicalRoutes.POST_CONDITION_ROUTE))
		.endRest()

		.get(IMMUNIZATIONS_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + IMMUNIZATIONS_URI)
			.to(direct(ClinicalRoutes.GET_IMMUNIZATIONS_ROUTE))
		.endRest()

		.get(PRESCRIPTIONS_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + PRESCRIPTIONS_URI)
			.to(direct(ClinicalRoutes.GET_PRESCRIPTIONS_ROUTE))
		.endRest();
	}
}
