package org.endeavourhealth.cim.camel.routes.domains;

import org.endeavourhealth.cim.camel.processors.clinical.*;
import org.endeavourhealth.cim.camel.routes.common.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ClinicalRoutes extends BaseRouteBuilder
{
    @Override
    public void configureRoute() throws Exception
	{
		final String GET_FULL_RECORD_ROUTE = "GetFullRecordRoute";
		final String GET_ALLERGY_INTOLERANCES_ROUTE = "GetAllergyIntolerancesRoute";
		final String GET_CONDITION_ROUTE = "GetConditionsRoute";
		final String POST_CONDITION_ROUTE = "AddConditionRoute";
		final String GET_IMMUNIZATIONS_ROUTE = "GetImmunizations";
		final String GET_MEDICATION_STATEMENTS_ROUTE = "GetMedicationStatements";
		final String GET_MEDICATION_ORDERS_ROUTE = "GetMedicationOrders";
		
		final String BASE_PATH = "/{odsCode}/Patient";

        final String ALLERGY_INTOLERANCE_URI = "/{id}/AllergyIntolerance";
		final String FULL_RECORD_URI = "/{id}/$everythingnobinary";
		final String CONDITION_URI = "/{id}/Condition";
		final String IMMUNIZATIONS_URI = "/{id}/Immunization";
		final String MEDICATION_STATEMENTS_URI = "/{id}/MedicationStatement";
		final String MEDICATION_ORDERS_URI = "/{id}/MedicationOrder";

		// endpoints
        rest(BASE_PATH)

        .get(ALLERGY_INTOLERANCE_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ALLERGY_INTOLERANCE_URI)
			.to(BaseRouteBuilder.direct(GET_ALLERGY_INTOLERANCES_ROUTE))
        .endRest()

		.get(FULL_RECORD_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + FULL_RECORD_URI)
			.to(BaseRouteBuilder.direct(GET_FULL_RECORD_ROUTE))
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

		.get(MEDICATION_STATEMENTS_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + MEDICATION_STATEMENTS_URI)
			.to(BaseRouteBuilder.direct(GET_MEDICATION_STATEMENTS_ROUTE))
		.endRest()

		.get(MEDICATION_ORDERS_URI)
			.route()
			.routeId(HttpVerb.GET + BASE_PATH + MEDICATION_ORDERS_URI)
			.to(BaseRouteBuilder.direct(GET_MEDICATION_ORDERS_ROUTE))
		.endRest();

		// routes
		buildWrappedRoute(CimCore.ROUTE_NAME, GET_ALLERGY_INTOLERANCES_ROUTE)
				.process(new GetAllergiesProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME, GET_FULL_RECORD_ROUTE)
				.process(new GetFullRecordProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME,GET_CONDITION_ROUTE)
				.process(new GetConditionsProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME, POST_CONDITION_ROUTE)
				.process(new AddConditionProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME, GET_IMMUNIZATIONS_ROUTE)
				.process(new GetImmunizationsProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME, GET_MEDICATION_STATEMENTS_ROUTE)
				.process(new GetMedicationStatementsProcessor());

		buildWrappedRoute(CimCore.ROUTE_NAME, GET_MEDICATION_ORDERS_ROUTE)
				.process(new GetMedicationOrdersProcessor());

	}
}
