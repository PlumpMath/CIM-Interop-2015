package org.endeavourhealth.common.camel.routes;

import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class MessageRouter extends BaseRouteBuilder {
	public static final String MessageRouterCallbackRoute = "MessageRouterCallback";
    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.MESSAGE_ROUTER))
            .routeId(ComponentRouteName.MESSAGE_ROUTER)
            .recipientList(exchangeProperty(MessageRouterCallbackRoute));
    }
}
