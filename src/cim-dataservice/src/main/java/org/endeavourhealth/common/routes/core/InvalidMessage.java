package org.endeavourhealth.common.routes.core;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class InvalidMessage extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.INVALID_MESSAGE))
            .routeId(CoreRouteName.INVALID_MESSAGE)
            .log(LoggingLevel.ERROR, "InvalidMessage")
            .to(Route.direct(CoreRouteName.RESPONSE))
            .stop();
    }
}
