package org.endeavourhealth.cim.routes.core;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CIMInvalidMessage extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CIM_INVALID_MESSAGE))
            .routeId(CoreRouteName.CIM_INVALID_MESSAGE)
            .log(LoggingLevel.ERROR, "InvalidMessage")
            .to(Route.direct(CoreRouteName.CIM_RESPONSE))
            .stop();
    }
}
