package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.clinical.*;
import org.endeavourhealth.cim.camel.routes.common.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ClinicalRoutes extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

		final String GET_ALLERGY_INTOLERANCES_ROUTE = "GetAllergyIntolerances";
		final String GET_CONDITION_ROUTE = "GetConditionsRoute";
		final String POST_CONDITION_ROUTE = "AddConditionRoute";
		final String GET_IMMUNIZATIONS_ROUTE = "GetImmunizations";
		final String GET_PRESCRIPTIONS_ROUTE = "GetMedicationPrescriptions";
		
		final String BASE_PATH = "/{odsCode}/Patient";

        final String ALLERGY_INTOLERANCE_URI = "/{id}/AllergyIntolerance";
		final String CONDITION_URI = "/{id}/Condition";
		final String IMMUNIZATIONS_URI = "/{id}/Immunization";
		final String PRESCRIPTIONS_URI = "/{id}/MedicationPrescription";

		// endpoints
        rest(BASE_PATH)

        .get(ALLERGY_INTOLERANCE_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ALLERGY_INTOLERANCE_URI)
			.to(BaseRouteBuilder.direct(GET_ALLERGY_INTOLERANCES_ROUTE))
        .endRest()

		.get(CONDITION_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + CONDITION_URI)
			.to(BaseRouteBuilder.direct(GET_CONDITION_ROUTE))
		.endRest()

		.post(CONDITION_URI)
			.route()
			.routeId(HttpVerb.POST + BASE_PATH + CONDITION_URI)
			.to(BaseRouteBuilder.direct(POST_CONDITION_ROUTE))
		.endRest()

		.get(IMMUNIZATIONS_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + IMMUNIZATIONS_URI)
			.to(BaseRouteBuilder.direct(GET_IMMUNIZATIONS_ROUTE))
		.endRest()

		.get(PRESCRIPTIONS_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + PRESCRIPTIONS_URI)
			.to(BaseRouteBuilder.direct(GET_PRESCRIPTIONS_ROUTE))
		.endRest();

		// routes
		buildWrappedRoute(CimCore.ROUTE_NAME, GET_ALLERGY_INTOLERANCES_ROUTE)
				.process(new GetAllergyIntolerancesProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME,GET_CONDITION_ROUTE)
				.process(new GetConditionsProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME,POST_CONDITION_ROUTE)
				.process(new AddConditionProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME,GET_IMMUNIZATIONS_ROUTE)
				.process(new GetImmunizationsProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME,GET_PRESCRIPTIONS_ROUTE)
				.process(new GetMedicationPrescriptionsProcessor());
	}
}
