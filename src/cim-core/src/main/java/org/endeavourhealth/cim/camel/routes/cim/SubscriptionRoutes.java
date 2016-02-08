package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.demographics.AddSubscriptionProcessor;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SubscriptionRoutes extends BaseRouteBuilder {
	public static final String ADD_SUBSCRIPTION_ROUTE = "AddSubscription";

    @Override
    public void configureRoute() throws Exception {
        buildWrappedRoute(CimCore.ROUTE_NAME, ADD_SUBSCRIPTION_ROUTE)
            .process(new AddSubscriptionProcessor());
    }
}
