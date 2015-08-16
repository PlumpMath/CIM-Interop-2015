package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CIMInvalidMessage extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_INVALID_MESSAGE))
            .routeId(CoreRouteName.CIM_INVALID_MESSAGE)
            .log(LoggingLevel.ERROR, "InvalidMessage")
            .to(Route.direct(CoreRouteName.CIM_RESPONSE))
            .stop();
    }
}
