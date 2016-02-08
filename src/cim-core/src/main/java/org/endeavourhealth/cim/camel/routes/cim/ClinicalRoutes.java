package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.clinical.*;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ClinicalRoutes extends BaseRouteBuilder {
	public static final String GET_ALLERGY_INTOLERANCES_ROUTE = "GetAllergyIntolerances";
	public static final String GET_CONDITION_ROUTE = "GetConditionsRoute";
	public static final String POST_CONDITION_ROUTE = "AddConditionRoute";
	public static final String GET_IMMUNIZATIONS_ROUTE = "GetImmunizations";
	public static final String GET_PRESCRIPTIONS_ROUTE = "GetMedicationPrescriptions";

    @Override
    public void configureRoute() throws Exception {
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
