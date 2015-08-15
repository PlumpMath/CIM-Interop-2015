package org.endeavourhealth.cim.routes.builders;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.routes.config.CoreRouteName;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings("unused")
public class CIMMessageRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_MESSAGE_ROUTER))
            .routeId(CoreRouteName.CIM_MESSAGE_ROUTER)
            .recipientList(header(HeaderKey.MessageRouterCallback))
            .to(Route.direct(CoreRouteName.CIM_MESSAGE_ROUTER_RESULT));
    }
}
