package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.clinical.*;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ClinicalRoutes extends CIMRouteBuilder {
	public static final String GET_ALLERGY_INTOLERANCES_ROUTE = "GetAllergyIntolerances";
	public static final String GET_CONDITION_ROUTE = "GetConditionsRoute";
	public static final String POST_CONDITION_ROUTE = "AddConditionRoute";
	public static final String GET_IMMUNIZATIONS_ROUTE = "GetImmunizations";
	public static final String GET_PRESCRIPTIONS_ROUTE = "GetMedicationPrescriptions";

    @Override
    public void configureRoute() throws Exception {
        buildCimCallbackRoute(GET_ALLERGY_INTOLERANCES_ROUTE)
            .process(new GetAllergyIntolerancesProcessor());

		buildCimCallbackRoute(GET_CONDITION_ROUTE)
			.process(new GetConditionsProcessor());

		buildCimCallbackRoute(POST_CONDITION_ROUTE)
			.process(new AddConditionProcessor());

		buildCimCallbackRoute(GET_IMMUNIZATIONS_ROUTE)
			.process(new GetImmunizationsProcessor());

		buildCimCallbackRoute(GET_PRESCRIPTIONS_ROUTE)
			.process(new GetMedicationPrescriptionsProcessor());

    }
}
