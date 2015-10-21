package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.demographics.AddSubscriptionProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SubscriptionRoutes extends BaseRouteBuilder {
	public static final String ADD_SUBSCRIPTION_ROUTE = "AddSubscription";

    @Override
    public void configureRoute() throws Exception {
        buildCallbackRoute(ADD_SUBSCRIPTION_ROUTE)
            .process(new AddSubscriptionProcessor());
    }
}
