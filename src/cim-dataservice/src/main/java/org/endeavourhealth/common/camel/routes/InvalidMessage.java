package org.endeavourhealth.common.camel.routes;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings({"WeakerAccess", "unused"})
public class InvalidMessage extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.INVALID_MESSAGE))
            .routeId(ComponentRouteName.INVALID_MESSAGE)
            .log(LoggingLevel.ERROR, "InvalidMessage")
            .to(direct(ComponentRouteName.RESPONSE))
            .stop();
    }
}
