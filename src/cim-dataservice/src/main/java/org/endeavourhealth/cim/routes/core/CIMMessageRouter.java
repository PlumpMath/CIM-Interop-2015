package org.endeavourhealth.cim.routes.core;

import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMMessageRouter extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CIM_MESSAGE_ROUTER))
            .routeId(CoreRouteName.CIM_MESSAGE_ROUTER)
            .recipientList(exchangeProperty(HeaderKey.MessageRouterCallback));
    }
}
