package org.endeavourhealth.common.routes.core;

import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;
import org.endeavourhealth.common.core.HeaderKey;

@SuppressWarnings("unused")
public class MessageRouter extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.MESSAGE_ROUTER))
            .routeId(CoreRouteName.MESSAGE_ROUTER)
            .recipientList(exchangeProperty(HeaderKey.MessageRouterCallback));
    }
}
