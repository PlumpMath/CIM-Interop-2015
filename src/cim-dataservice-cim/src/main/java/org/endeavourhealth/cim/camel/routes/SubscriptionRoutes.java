package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.cim.processor.demographics.AddSubscriptionProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SubscriptionRoutes extends BaseRouteBuilder {
	public static final String ADD_SUBSCRIPTION_ROUTE = "AddSubscription";

    @Override
    public void configureRoute() throws Exception {
        buildCallbackRoute(CimCore.ROUTE_NAME, ADD_SUBSCRIPTION_ROUTE)
            .process(new AddSubscriptionProcessor());
    }
}
