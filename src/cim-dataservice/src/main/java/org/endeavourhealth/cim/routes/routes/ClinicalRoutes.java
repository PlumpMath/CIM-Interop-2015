package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.clinical.*;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ClinicalRoutes extends BaseRouteBuilder {
	public static final String GET_ALLERGY_INTOLERANCES_ROUTE = "GetAllergyIntolerances";
	public static final String GET_CONDITION_ROUTE = "GetConditionsRoute";
	public static final String POST_CONDITION_ROUTE = "AddConditionRoute";
	public static final String GET_IMMUNIZATIONS_ROUTE = "GetImmunizations";
	public static final String GET_PRESCRIPTIONS_ROUTE = "GetMedicationPrescriptions";

    @Override
    public void configureRoute() throws Exception {
        buildCallbackRoute(GET_ALLERGY_INTOLERANCES_ROUTE)
            .process(new GetAllergyIntolerancesProcessor());

		buildCallbackRoute(GET_CONDITION_ROUTE)
			.process(new GetConditionsProcessor());

		buildCallbackRoute(POST_CONDITION_ROUTE)
			.process(new AddConditionProcessor());

		buildCallbackRoute(GET_IMMUNIZATIONS_ROUTE)
			.process(new GetImmunizationsProcessor());

		buildCallbackRoute(GET_PRESCRIPTIONS_ROUTE)
			.process(new GetMedicationPrescriptionsProcessor());

    }
}
