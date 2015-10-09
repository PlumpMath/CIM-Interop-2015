package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.demographics.AddSubscriptionProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SubscriptionRoutes extends CIMRouteBuilder {
	public static final String ADD_SUBSCRIPTION_ROUTE = "AddSubscription";

    @Override
    public void configureRoute() throws Exception {
        buildCimCallbackRoute(ADD_SUBSCRIPTION_ROUTE)
            .process(new AddSubscriptionProcessor());
    }
}
